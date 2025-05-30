package com.korean.koreanairDiet.domain.feedback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackStatsResponse { // 전체 메뉴에 대한 피드백 통계 응답 DTO -> 사용 안함
    private Long menuId;
    private String menuName;
    private FeedbackResponse feedback;
}