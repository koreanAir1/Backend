package com.korean.koreanairDiet.domain.menu.entity;

import com.korean.koreanairDiet.domain.feedback.entity.Feedback;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "menu")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_menu")
    private Long idMenu; // pk

    @Column(name = "menu_line", length = 10)
    private String menuLine; // 배식 라인

    @Column(name = "menu_kcal")
    private Integer menuKcal; // 칼로리

    @Column(name = "menu_nutri", length = 15)
    private String menuNutri; // 탄단지비율 예시 55:22:23

    @Column(name = "menu_name", length = 200)
    private String menuName; // 메뉴 이름

    @Column(name = "weekday", length = 10)
    private String weekday; // 'MON', 'TUE', 'WED', 'THU', 'FRI' 평일만

    @Column(name = "menu_date")
    private LocalDate menuDate; // 실제 메뉴 제공 날짜

    @Column(name = "menu_liked")
    private Integer menuLiked; // 선호도 개수

    @Column(name = "menu_imgURL", length = 200)
    private String menuImgUrl; // 메뉴 이미지 URL

    @Column(name = "menu_type", length = 10)
    private String menuType; // 'REGULAR', 'GRSAB_AND_GO' 등으로 구분S

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_feedback")
    private Feedback feedback;
}