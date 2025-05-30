package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.CommonMenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuResponse;

import java.util.List;

public interface CommonMenuService {
    CommonMenuResponse getCommonMenuById(Long commonMenuId);
    List<CommonMenuResponse> getAllCommonMenus();
    List<CommonMenuResponse> getCommonMenusByWeekday(String weekday);
    CommonMenuResponse addCommonMenu(CommonMenuRequest request);
    CommonMenuResponse updateCommonMenu(Long commonMenuId, CommonMenuRequest request);
    void deleteCommonMenu(Long commonMenuId);
}
