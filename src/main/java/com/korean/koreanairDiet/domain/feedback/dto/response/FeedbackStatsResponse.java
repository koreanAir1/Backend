package com.korean.koreanairDiet.domain.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackStatsResponse {
    private Long menuId;
    private String menuName;
    private FeedbackResponse feedback;
}