package com.korean.koreanairDiet.domain.feedback.entity;

import com.korean.koreanairDiet.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_feedback")
    private Long idFeedback;

    @Column(name = "feedback_salty")
    private Integer feedbackSalty;

    @Column(name = "feedback_spicy")
    private Integer feedbackSpicy;

    @Column(name = "feedback_sweet")
    private Integer feedbackSweet;

    @Column(name = "feedback_much")
    private Integer feedbackMuch;

    @Column(name = "feedback_less")
    private Integer feedbackLess;

    @Column(name = "feedback_good")
    private Integer feedbackGood;

    @Column(name = "feedback_soso")
    private Integer feedbackSoso;

    @Column(name = "feedback_bad")
    private Integer feedbackBad;

    @OneToOne(mappedBy = "feedback")
    private Menu menu;
}