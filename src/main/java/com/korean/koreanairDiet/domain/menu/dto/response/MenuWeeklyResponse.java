package com.korean.koreanairDiet.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuWeeklyResponse {
    private String weekday;
    private MenuDayResponse breakfast;
    private MenuDayResponse lunch;
    private MenuDayResponse dinner;
    private MenuDayResponse grabAndGo;
}