package com.korean.koreanairDiet.domain.recommendation.controller;

import com.korean.koreanairDiet.domain.recommendation.dto.request.DietRecommendationRequest;
import com.korean.koreanairDiet.domain.recommendation.dto.response.DietRecommendationResponse;
import com.korean.koreanairDiet.domain.recommendation.service.DietRecommendationService;
import com.korean.koreanairDiet.global.response.ResponseCode;
import com.korean.koreanairDiet.global.response.ResponseForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "AI 식단 추천 API", description = "사용자 선호도 기반 AI 식단 추천 API")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/diet-recommendation")
public class DietRecommendationController {

    private final DietRecommendationService dietRecommendationService;

    @Operation(summary = "AI 식단 추천", description = "사용자의 선호도를 기반으로 AI가 식단을 추천합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 식단 추천 완료",
            content = @Content(schema = @Schema(implementation = DietRecommendationResponse.class)))
    @ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청")
    @PostMapping
    public ResponseEntity<ResponseForm> recommendDiet(@RequestBody DietRecommendationRequest request) {
        log.info("받은 추천 요청: {}", request);
        DietRecommendationResponse response = dietRecommendationService.recommendDiet(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_RECOMMENDATION_SUCCESS, response));
    }
}
