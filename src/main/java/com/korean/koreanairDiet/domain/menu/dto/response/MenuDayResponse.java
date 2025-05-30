package com.korean.koreanairDiet.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDayResponse {
    private Long idMenu;
    private String menuName;
    private Integer menuKcal;
    private String menuNutri;
    private Integer menuLiked;
    private String menuImgUrl;
}