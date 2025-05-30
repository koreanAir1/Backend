package com.korean.koreanairDiet.domain.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}