package com.korean.koreanairDiet.domain.feedback.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor //사용자가 메뉴에 대한 피드백을 작성하였을때 백엔드에서 받아들이는 DTO
public class FeedbackRequest {
    private List<String> feedbackType; // 받은 피드백 전부 리스트 형태로 받아옴 ["salty", "spicy", "sweet"]
    private Long menuId;
}