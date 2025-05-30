package com.korean.koreanairDiet.domain.feedback.controller;

import com.korean.koreanairDiet.domain.feedback.dto.request.FeedbackRequest;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackResponse;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackStatsResponse;
import com.korean.koreanairDiet.domain.feedback.service.FeedbackService;
import com.korean.koreanairDiet.global.response.ResponseCode;
import com.korean.koreanairDiet.global.response.ResponseForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "피드백 API", description = "메뉴별 피드백 관련 API")
@RequestMapping("/api/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Operation(summary = "피드백 추가", description = "메뉴에 대한 피드백을 추가합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 피드백이 성공적으로 추가됨", content = @Content(schema = @Schema(implementation = FeedbackResponse.class)))
    @ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청이거나 메뉴가 존재하지 않음")
    @PostMapping
    public ResponseEntity<ResponseForm> addFeedback(@RequestBody FeedbackRequest request) {
        FeedbackResponse response = feedbackService.addFeedback(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.FEEDBACK_ADD_SUCCESS, response));
    }

    @Operation(summary = "피드백 조회", description = "특정 ID의 피드백 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 피드백 정보 조회 성공", content = @Content(schema = @Schema(implementation = FeedbackResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 피드백 정보를 찾을 수 없음")
    @GetMapping("/{feedbackId}")
    public ResponseEntity<ResponseForm> getFeedback(@PathVariable Long feedbackId) {
        FeedbackResponse response = feedbackService.getFeedbackById(feedbackId);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.FEEDBACK_GET_SUCCESS, response));
    }

    // @Operation(summary = "전체 피드백 통계 조회", description = "모든 메뉴의 피드백 통계를 조회합니다")
    // @ApiResponse(responseCode = "200", description = "성공 - 피드백 통계 목록 조회 성공", content = @Content(schema = @Schema(implementation = FeedbackStatsResponse.class)))
    // @GetMapping("/stats")
    // public ResponseEntity<ResponseForm> getAllFeedbackStats() {
    //     List<FeedbackStatsResponse> responses = feedbackService.getAllFeedbackStats();
    //     return ResponseEntity.ok(ResponseForm.of(ResponseCode.FEEDBACK_STATS_SUCCESS, responses));
    // }
}