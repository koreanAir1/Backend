package com.korean.koreanairDiet.domain.recommendation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietRecommendationResponse {
    private String menu;
    private String description;
}
