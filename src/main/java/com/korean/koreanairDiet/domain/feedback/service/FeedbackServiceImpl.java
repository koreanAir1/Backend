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

                // 피드백이 null인 경우 새 피드백 생성
                if (feedback == null) {
                        feedback = Feedback.builder()
                        .menu(menu) 
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

                // 리스트에 있는 모든 피드백 타입에 대해 카운트 증가
                for (String type : request.getFeedbackType()) {
                        switch (type) {
                        case "salty" -> feedback = feedback.toBuilder()
                                .feedbackSalty(feedback.getFeedbackSalty() + 1)
                                .build();
                        case "spicy" -> feedback = feedback.toBuilder()
                                .feedbackSpicy(feedback.getFeedbackSpicy() + 1)
                                .build();
                        case "sweet" -> feedback = feedback.toBuilder()
                                .feedbackSweet(feedback.getFeedbackSweet() + 1)
                                .build();
                        case "much" -> feedback = feedback.toBuilder()
                                .feedbackMuch(feedback.getFeedbackMuch() + 1)
                                .build();
                        case "less" -> feedback = feedback.toBuilder()
                                .feedbackLess(feedback.getFeedbackLess() + 1)
                                .build();
                        case "good" -> feedback = feedback.toBuilder()
                                .feedbackGood(feedback.getFeedbackGood() + 1)
                                .build();
                        case "soso" -> feedback = feedback.toBuilder()
                                .feedbackSoso(feedback.getFeedbackSoso() + 1)
                                .build();
                        case "bad" -> feedback = feedback.toBuilder()
                                .feedbackBad(feedback.getFeedbackBad() + 1)
                                .build();
                        // 필요시 추가 타입 처리
                        }
                }

                feedback = feedbackRepository.save(feedback);

                // 메뉴와 피드백 연결
                menu.setFeedback(feedback);
                menuRepository.save(menu);

                return mapToFeedbackResponse(feedback);
        }

        @Override // 피드백 ID로 피드백 조회
        public FeedbackResponse getFeedbackById(Long feedbackId) {
                Feedback feedback = feedbackRepository.findById(feedbackId)
                                .orElseThrow(() -> new RuntimeException("피드백 정보를 찾을 수 없습니다."));
                return mapToFeedbackResponse(feedback);
        }

        // @Override // 전체 메뉴의 피드백 통계 조회
        // public List<FeedbackStatsResponse> getAllFeedbackStats() {
        // List<Menu> menus = menuRepository.findAll();
        // return menus.stream()
        // .filter(menu -> menu.getFeedback() != null)
        // .map(menu -> FeedbackStatsResponse.builder()
        // .menuId(menu.getIdMenu())
        // .menuName(menu.getMenuName())
        // .feedback(mapToFeedbackResponse(menu.getFeedback()))
        // .build())
        // .collect(Collectors.toList());
        // }


        // 피드백 엔터티를 DTO 객체로 변환하는 메서드
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
                                .idMenu(feedback.getMenu().getIdMenu())
                                .build();
        }
}