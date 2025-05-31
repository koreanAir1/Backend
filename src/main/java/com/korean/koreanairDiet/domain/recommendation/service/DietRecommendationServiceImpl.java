package com.korean.koreanairDiet.domain.recommendation.service;

import com.korean.koreanairDiet.domain.menu.entity.Menu;
import com.korean.koreanairDiet.domain.menu.repository.MenuRepository;
import com.korean.koreanairDiet.domain.recommendation.dto.request.DietRecommendationRequest;
import com.korean.koreanairDiet.domain.recommendation.dto.response.DietRecommendationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DietRecommendationServiceImpl implements DietRecommendationService {

    private final ChatModel chatModel;
    private final MenuRepository menuRepository;

    @Override
    public DietRecommendationResponse recommendDiet(DietRecommendationRequest request) {
        // 해당 날짜의 메뉴 목록 조회
        LocalDate menuDate = request.getMenuDate() != null ? request.getMenuDate() : LocalDate.now();
        List<Menu> todayMenus = menuRepository.findByMenuDate(menuDate);

        if (todayMenus.isEmpty()) {
            log.warn("No menus found for date: {}", menuDate);
            return DietRecommendationResponse.builder()
                    .menu("메뉴 없음")
                    .description("해당 날짜에 제공되는 메뉴가 없습니다.")
                    .build();
        }

        // 메뉴 목록을 문자열로 변환
        String menuList = todayMenus.stream()
                .map(menu -> String.format("- %s (라인: %s, 칼로리: %d kcal, 영양성분: %s, 유형: %s)",
                        menu.getMenuName(),
                        menu.getMenuLine(),
                        menu.getMenuKcal(),
                        menu.getMenuNutri(),
                        menu.getMenuType()))
                .collect(Collectors.joining("\n"));

        // 프롬프트 템플릿 생성
        String promptText = """
                다음은 사용자의 식단 선호 정보입니다.
                음식 유형: {foodType}
                짠맛: {salty}/100
                매운맛: {spicy}/100
                단맛: {sweet}/100
                싱겁다: {bland}/100
                기타: {comment}
                
                오늘 제공하는 메뉴 목록:
                {menuList}
                
                위 메뉴 목록 중에서 사용자의 선호도를 고려하여 가장 적합한 메뉴 1가지를 추천해주세요.
                선택한 메뉴가 사용자의 어떤 선호도에 부합하는지 간단히 설명해주세요.
                """;

        // PromptTemplate 사용하여 변수 적용
        PromptTemplate template = new PromptTemplate(promptText);
        template.add("foodType", request.getFoodType());
        template.add("salty", String.valueOf(request.getSalty()));
        template.add("spicy", String.valueOf(request.getSpicy()));
        template.add("sweet", String.valueOf(request.getSweet()));
        template.add("bland", String.valueOf(request.getBland()));
        template.add("comment", request.getComment());
        template.add("menuList", menuList);

        // 메시지 생성
        String message = template.render();
        Message userMessage = new UserMessage(message);
        Message systemMessage = new SystemMessage("결과는 다음과 같은 형식으로 제공해줘:\n메뉴: [메뉴명에서 첫번째 음식 이름]\n설명: [설명]");

        // AI 호출
        String content = chatModel.call(userMessage, systemMessage);
        log.info("AI Response: {}", content);

        // 응답 파싱
        return parseAIResponse(content);
    }

    private DietRecommendationResponse parseAIResponse(String aiResponse) {
        // 간단한 파싱 로직
        String[] lines = aiResponse.split("\n");

        String menu = "";
        String description = "";

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty()) continue;

            if (line.startsWith("메뉴:")) {
                menu = line.substring(line.indexOf(":") + 1).trim();
            } else if (line.startsWith("설명:")) {
                description = line.substring(line.indexOf(":") + 1).trim();
            }
        }

        return DietRecommendationResponse.builder()
                .menu(menu)
                .description(description)
                .build();
    }
}