package com.korean.koreanairDiet.domain.recommendation.service;

import com.korean.koreanairDiet.domain.recommendation.dto.request.DietRecommendationRequest;
import com.korean.koreanairDiet.domain.recommendation.dto.response.DietRecommendationResponse;

public interface DietRecommendationService {
    DietRecommendationResponse recommendDiet(DietRecommendationRequest request);
}
