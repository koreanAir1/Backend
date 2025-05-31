package com.korean.koreanairDiet.domain.feedback.service;

import com.korean.koreanairDiet.domain.feedback.dto.request.FeedbackRequest;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackResponse;
import com.korean.koreanairDiet.domain.feedback.dto.response.FeedbackStatsResponse;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse addFeedback(FeedbackRequest request);
    FeedbackResponse getFeedbackById(Long feedbackId);
    //List<FeedbackStatsResponse> getAllFeedbackStats();
}
