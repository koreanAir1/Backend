package com.korean.koreanairDiet.domain.menu.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuLineResponse {
    private MenuDayResponse menuInfo;
    private String lines; // "A/B" 또는 "C" 또는 "E/F" 등
}
