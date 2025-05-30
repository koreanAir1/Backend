package com.korean.koreanairDiet.domain.menu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String menuImgUrl;
    private String menuType; // 'REGULAR', 'GRAB_AND_GO'
}