package com.korean.koreanairDiet.domain.feedback.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequest {
    private String feedbackType; // "salty", "spicy", "sweet" 등
    private Long menuId;
}