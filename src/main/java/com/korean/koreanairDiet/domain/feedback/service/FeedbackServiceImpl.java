package com.korean.koreanairDiet.domain.feedback.service;

import com.korean.koreanairDiet.domain.feedback.dto.request.FeedbackRequest;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackResponse;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackStatsResponse;
import com.korean.koreanairDiet.domain.feedback.entity.Feedback;
import com.korean.koreanairDiet.domain.feedback.repository.FeedbackRepository;
import com.korean.koreanairDiet.domain.menu.entity.Menu;
import com.korean.koreanairDiet.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final MenuRepository menuRepository;

    @Override
    @Transactional
    public FeedbackResponse addFeedback(FeedbackRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        Feedback feedback = menu.getFeedback();

        if (feedback == null) {
            feedback = Feedback.builder()
                    .feedbackSalty(0)
                    .feedbackSpicy(0)
                    .feedbackSweet(0)
                    .feedbackMuch(0)
                    .feedbackLess(0)
                    .feedbackGood(0)
                    .feedbackSoso(0)
                    .feedbackBad(0)
                    .build();
        }

        // 피드백 타입에 따라 카운트 증가
        switch (request.getFeedbackType()) {
            case "salty" -> feedback = Feedback.builder()
                    .idFeedback(feedback.getIdFeedback())
                    .feedbackSalty(feedback.getFeedbackSalty() + 1)
                    .feedbackSpicy(feedback.getFeedbackSpicy())
                    .feedbackSweet(feedback.getFeedbackSweet())
                    .feedbackMuch(feedback.getFeedbackMuch())
                    .feedbackLess(feedback.getFeedbackLess())
                    .feedbackGood(feedback.getFeedbackGood())
                    .feedbackSoso(feedback.getFeedbackSoso())
                    .feedbackBad(feedback.getFeedbackBad())
                    .build();
            case "spicy" -> feedback = Feedback.builder()
                    .idFeedback(feedback.getIdFeedback())
                    .feedbackSalty(feedback.getFeedbackSalty())
                    .feedbackSpicy(feedback.getFeedbackSpicy() + 1)
                    .feedbackSweet(feedback.getFeedbackSweet())
                    .feedbackMuch(feedback.getFeedbackMuch())
                    .feedbackLess(feedback.getFeedbackLess())
                    .feedbackGood(feedback.getFeedbackGood())
                    .feedbackSoso(feedback.getFeedbackSoso())
                    .feedbackBad(feedback.getFeedbackBad())
                    .build();
            // 나머지 피드백 타입도 동일한 방식으로 처리
            // ...
        }

        feedback = feedbackRepository.save(feedback);

        // 메뉴와 피드백 연결
        menu.setFeedback(feedback);
        menuRepository.save(menu);

        return mapToFeedbackResponse(feedback);
    }

    @Override
    public FeedbackResponse getFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("피드백 정보를 찾을 수 없습니다."));
        return mapToFeedbackResponse(feedback);
    }

    @Override
    public List<FeedbackStatsResponse> getAllFeedbackStats() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .filter(menu -> menu.getFeedback() != null)
                .map(menu -> FeedbackStatsResponse.builder()
                        .menuId(menu.getIdMenu())
                        .menuName(menu.getMenuName())
                        .feedback(mapToFeedbackResponse(menu.getFeedback()))
                        .build())
                .collect(Collectors.toList());
    }

    private FeedbackResponse mapToFeedbackResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .idFeedback(feedback.getIdFeedback())
                .feedbackSalty(feedback.getFeedbackSalty())
                .feedbackSpicy(feedback.getFeedbackSpicy())
                .feedbackSweet(feedback.getFeedbackSweet())
                .feedbackMuch(feedback.getFeedbackMuch())
                .feedbackLess(feedback.getFeedbackLess())
                .feedbackGood(feedback.getFeedbackGood())
                .feedbackSoso(feedback.getFeedbackSoso())
                .feedbackBad(feedback.getFeedbackBad())
                .build();
    }
}