package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.MenuLikeRequest;
import com.korean.koreanairDiet.domain.menu.dto.request.MenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuWeeklyResponse;

import java.util.List;

public interface MenuService {
    MenuResponse getMenuById(Long menuId);
    List<MenuWeeklyResponse> getWeeklyMenus();
    MenuResponse addMenu(MenuRequest request);
    MenuResponse updateMenu(Long menuId, MenuRequest request);
    void deleteMenu(Long menuId);
    MenuResponse likeMenu(MenuLikeRequest request);
    MenuResponse dislikeMenu(MenuLikeRequest request);
    List<MenuResponse> getTodayMenus(String weekday);
}