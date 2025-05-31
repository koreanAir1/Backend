package com.korean.koreanairDiet.domain.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor // 피드백 정보를 담은 DTO, 프론트에 하나의 메뉴에 대한 피드백 정보(각 요소별 카운트값)을 전달, 이후 프론트에서 워드 클라우드 만들때 사용
public class FeedbackResponse {
    private Long idFeedback;
    private Integer feedbackSalty;
    private Integer feedbackSpicy;
    private Integer feedbackSweet;
    private Integer feedbackMuch;
    private Integer feedbackLess;
    private Integer feedbackGood;
    private Integer feedbackSoso;
    private Integer feedbackBad;
    private Long idMenu;
}