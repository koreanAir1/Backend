package com.korean.koreanairDiet.domain.recommendation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DietRecommendationRequest {
    private String foodType;   // "밥" 또는 "면"
    private Integer salty;     // 짠맛 (0-100)
    private Integer spicy;     // 매운맛 (0-100)
    private Integer sweet;     // 단맛 (0-100)
    private Integer bland;     // 싱거운맛 (0-100)
    private String comment;    // 추가 의견
}