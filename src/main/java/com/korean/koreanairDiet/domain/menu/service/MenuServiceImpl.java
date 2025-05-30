package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.MenuLikeRequest;
import com.korean.koreanairDiet.domain.menu.dto.request.MenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuDayResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuRankResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuWeeklyResponse;
import com.korean.koreanairDiet.domain.menu.entity.Menu;
import com.korean.koreanairDiet.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    @Override
    public MenuResponse getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));
        return mapToMenuResponse(menu);
    }

    @Override
    public List<MenuResponse> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();
        return menus.stream()
                .map(this::mapToMenuResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuWeeklyResponse> getWeeklyMenus() {
        List<String> weekdays = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일");

        return weekdays.stream()
                .map(weekday -> {
                    // 아침 메뉴
                    List<Menu> breakfastMenus = menuRepository.findByWeekdayAndMenuLine(weekday, "아침");
                    MenuDayResponse breakfast = breakfastMenus.isEmpty() ? null :
                            mapToMenuDayResponse(breakfastMenus.get(0));

                    // 점심 메뉴
                    List<Menu> lunchMenus = menuRepository.findByWeekdayAndMenuLine(weekday, "점심");
                    MenuDayResponse lunch = lunchMenus.isEmpty() ? null :
                            mapToMenuDayResponse(lunchMenus.get(0));

                    // 저녁 메뉴
                    List<Menu> dinnerMenus = menuRepository.findByWeekdayAndMenuLine(weekday, "저녁");
                    MenuDayResponse dinner = dinnerMenus.isEmpty() ? null :
                            mapToMenuDayResponse(dinnerMenus.get(0));

                    // 그랩앤고 메뉴
                    List<Menu> grabMenus = menuRepository.findByWeekdayAndMenuType(weekday, "GRAB_AND_GO");
                    MenuDayResponse grabAndGo = grabMenus.isEmpty() ? null :
                            mapToMenuDayResponse(grabMenus.get(0));

                    return MenuWeeklyResponse.builder()
                            .weekday(weekday)
                            .breakfast(breakfast)
                            .lunch(lunch)
                            .dinner(dinner)
                            .grabAndGo(grabAndGo)
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuResponse addMenu(MenuRequest request) {
        Menu menu = Menu.builder()
                .menuLine(request.getMenuLine())
                .menuKcal(request.getMenuKcal())
                .menuNutri(request.getMenuNutri())
                .menuName(request.getMenuName())
                .weekday(request.getWeekday())
                .menuLiked(0)  // 새로운 메뉴는 선호도 0으로 초기화
                .menuImgUrl(request.getMenuImgUrl())
                .menuType(request.getMenuType())
                .build();

        Menu savedMenu = menuRepository.save(menu);
        return mapToMenuResponse(savedMenu);
    }

    @Override
    @Transactional
    public MenuResponse updateMenu(Long menuId, MenuRequest request) {
        Menu existingMenu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        Menu updatedMenu = Menu.builder()
                .idMenu(existingMenu.getIdMenu())
                .menuLine(request.getMenuLine())
                .menuKcal(request.getMenuKcal())
                .menuNutri(request.getMenuNutri())
                .menuName(request.getMenuName())
                .weekday(request.getWeekday())
                .menuLiked(existingMenu.getMenuLiked())  // 기존 선호도 유지
                .menuImgUrl(request.getMenuImgUrl())
                .menuType(request.getMenuType())
                .feedback(existingMenu.getFeedback())  // 기존 피드백 정보 유지
                .build();

        Menu savedMenu = menuRepository.save(updatedMenu);
        return mapToMenuResponse(savedMenu);
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        if (!menuRepository.existsById(menuId)) {
            throw new RuntimeException("메뉴를 찾을 수 없습니다.");
        }
        menuRepository.deleteById(menuId);
    }

    @Override
    @Transactional
    public MenuResponse likeMenu(MenuLikeRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        Menu updatedMenu = Menu.builder()
                .idMenu(menu.getIdMenu())
                .menuLine(menu.getMenuLine())
                .menuKcal(menu.getMenuKcal())
                .menuNutri(menu.getMenuNutri())
                .menuName(menu.getMenuName())
                .weekday(menu.getWeekday())
                .menuLiked(menu.getMenuLiked() + 1)  // 선호도 증가
                .menuImgUrl(menu.getMenuImgUrl())
                .menuType(menu.getMenuType())
                .feedback(menu.getFeedback())
                .build();

        Menu savedMenu = menuRepository.save(updatedMenu);
        return mapToMenuResponse(savedMenu);
    }

    @Override
    public List<MenuRankResponse> getMenuRanking() {
        List<Menu> menus = menuRepository.findAllOrderByMenuLikedDesc();
        return menus.stream()
                .map(menu -> MenuRankResponse.builder()
                        .idMenu(menu.getIdMenu())
                        .menuName(menu.getMenuName())
                        .menuLiked(menu.getMenuLiked())
                        .menuType(menu.getMenuType())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuResponse> getTodayMenus(String weekday) {
        if (weekday == null || weekday.isEmpty()) {
            // 요일 지정이 없으면 오늘 요일로 설정
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            weekday = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        }

        List<Menu> menus = menuRepository.findByWeekday(weekday);
        return menus.stream()
                .map(this::mapToMenuResponse)
                .collect(Collectors.toList());
    }

    private MenuResponse mapToMenuResponse(Menu menu) {
        return MenuResponse.builder()
                .idMenu(menu.getIdMenu())
                .menuLine(menu.getMenuLine())
                .menuKcal(menu.getMenuKcal())
                .menuNutri(menu.getMenuNutri())
                .menuName(menu.getMenuName())
                .weekday(menu.getWeekday())
                .menuLiked(menu.getMenuLiked())
                .menuImgUrl(menu.getMenuImgUrl())
                .menuType(menu.getMenuType())
                .build();
    }

    private MenuDayResponse mapToMenuDayResponse(Menu menu) {
        return MenuDayResponse.builder()
                .idMenu(menu.getIdMenu())
                .menuName(menu.getMenuName())
                .menuKcal(menu.getMenuKcal())
                .menuNutri(menu.getMenuNutri())
                .menuLiked(menu.getMenuLiked())
                .menuImgUrl(menu.getMenuImgUrl())
                .build();
    }
}
