package com.korean.koreanairDiet.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonMenuWeeklyResponse {
    private String weekday;
    private List<CommonMenuResponse> commonMenus;
}
