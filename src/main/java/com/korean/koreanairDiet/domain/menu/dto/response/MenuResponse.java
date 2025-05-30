package com.korean.koreanairDiet.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {
    private Long idMenu;
    private String menuLine;
    private Integer menuKcal;
    private String menuNutri;
    private String menuName;
    private String weekday;
    private Integer menuLiked;
    private String menuImgUrl;
    private String menuType;
}