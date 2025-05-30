package com.korean.koreanairDiet.domain.feedback.repository;

import com.korean.koreanairDiet.domain.feedback.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    // 필요한 추가 쿼리 메서드 정의
}
