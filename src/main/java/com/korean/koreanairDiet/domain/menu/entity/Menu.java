package com.korean.koreanairDiet.domain.menu.entity;

import com.korean.koreanairDiet.domain.feedback.entity.Feedback;
import jakarta.persistence.*;
import lombok.*;

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
    private Long idMenu;

    @Column(name = "menu_line", length = 5)
    private String menuLine;

    @Column(name = "menu_kcal")
    private Integer menuKcal;

    @Column(name = "menu_nutri", length = 15)
    private String menuNutri;

    @Column(name = "menu_name", length = 200)
    private String menuName;

    @Column(name = "weekday", length = 10)
    private String weekday;

    @Column(name = "menu_liked")
    private Integer menuLiked;

    @Column(name = "menu_imgURL", length = 200)
    private String menuImgUrl;

    @Column(name = "menu_type", length = 10)
    private String menuType; // 'REGULAR', 'GRAB_AND_GO' 등으로 구분

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_feedback")
    private Feedback feedback;
}