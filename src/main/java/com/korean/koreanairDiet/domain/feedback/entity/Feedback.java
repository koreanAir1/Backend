package com.korean.koreanairDiet.domain.feedback.entity;

import com.korean.koreanairDiet.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback") // 피드백 ID, 기본 키, 자동 증가, 메뉴엔터티의 id_feedback으로 검색시 사용
    private Long idFeedback;

    @Column(name = "feedback_salty") // 메뉴 피드백에 '짜다'를 선택한 사람의 수
    private Integer feedbackSalty;

    @Column(name = "feedback_spicy") // 메뉴 피드백에 '맵다'를 선택한 사람의 수
    private Integer feedbackSpicy;

    @Column(name = "feedback_sweet") // 메뉴 피드백에 '달다'를 선택한 사람의 수
    private Integer feedbackSweet;

    @Column(name = "feedback_much") // 메뉴 피드백에 '양이 많다'를 선택한 사람의 수
    private Integer feedbackMuch;

    @Column(name = "feedback_less") // 메뉴 피드백에 '양이 적다'를 선택한 사람의 수
    private Integer feedbackLess;

    @Column(name = "feedback_good") // 메뉴 피드백에 '맛있다'를 선택한 사람의 수
    private Integer feedbackGood;

    @Column(name = "feedback_soso") // 메뉴 피드백에 '보통이다'를 선택한 사람의 수
    private Integer feedbackSoso;

    @Column(name = "feedback_bad") // 메뉴 피드백에 '맛없다'를 선택한 사람의 수
    private Integer feedbackBad;

    @OneToOne(mappedBy = "feedback") // 메뉴와 1:1 관계
    private Menu menu;
}