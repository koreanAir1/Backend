package com.korean.koreanairDiet.domain.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String menuLine;
    private Integer menuKcal;
    private String menuNutri;
    private String menuName;
    private String weekday;
    private LocalDate menuDate;
    private String menuImgUrl;
    private String menuType; // 'REGULAR', 'GRAB_AND_GO'
}