package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.MenuLikeRequest;
import com.korean.koreanairDiet.domain.menu.dto.request.MenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuDayResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuLineResponse;
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
import java.time.temporal.TemporalAdjusters;
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
    public List<MenuWeeklyResponse> getWeeklyMenus() {
        List<String> weekdays = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일");

        // 이번 주 시작일을 구합니다 (월요일 기준)
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        return weekdays.stream()
                .map(weekday -> {
                    // 해당 요일의 날짜 계산
                    int dayOffset = weekdays.indexOf(weekday);
                    LocalDate menuDate = startOfWeek.plusDays(dayOffset);

                    // 해당 요일과 날짜에 맞는 일반 메뉴 조회
                    List<Menu> regularMenus = menuRepository.findByWeekdayAndMenuDateAndMenuType(
                            weekday, menuDate, "REGULAR");

                    // 메뉴별로 정보를 변환
                    List<MenuLineResponse> menuLineResponses = regularMenus.stream()
                            .map(menu -> MenuLineResponse.builder()
                                    .menuInfo(mapToMenuDayResponse(menu))
                                    .lines(menu.getMenuLine())
                                    .build())
                            .collect(Collectors.toList());

                    // 그랩앤고 메뉴
                    List<Menu> grabMenus = menuRepository.findByWeekdayAndMenuDateAndMenuType(
                            weekday, menuDate, "GRAB_AND_GO");
                    MenuDayResponse grabAndGo = grabMenus.isEmpty() ? null :
                            mapToMenuDayResponse(grabMenus.get(0));

                    // 각 요일의 메뉴 정보를 반환
                    return MenuWeeklyResponse.builder()
                            .weekday(weekday)
                            .menus(menuLineResponses)
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
                .menuDate(request.getMenuDate()) // menuDate 필드 추가
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
                .menuDate(request.getMenuDate()) // menuDate 필드 추가
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
                .menuDate(menu.getMenuDate()) // menuDate 필드 유지
                .menuLiked(menu.getMenuLiked() + 1)  // 선호도 증가
                .menuImgUrl(menu.getMenuImgUrl())
                .menuType(menu.getMenuType())
                .feedback(menu.getFeedback())
                .build();

        Menu savedMenu = menuRepository.save(updatedMenu);
        return mapToMenuResponse(savedMenu);
    }

    @Override
    @Transactional
    public MenuResponse dislikeMenu(MenuLikeRequest request) {
        Menu menu = menuRepository.findById(request.getMenuId())
                .orElseThrow(() -> new RuntimeException("메뉴를 찾을 수 없습니다."));

        Menu updatedMenu = Menu.builder()
                .idMenu(menu.getIdMenu())
                .menuLine(menu.getMenuLine())
                .menuKcal(menu.getMenuKcal())
                .menuNutri(menu.getMenuNutri())
                .menuName(menu.getMenuName())
                .weekday(menu.getWeekday())
                .menuDate(menu.getMenuDate()) // menuDate 필드 유지
                .menuLiked(menu.getMenuLiked() - 1)  // 선호도 증가
                .menuImgUrl(menu.getMenuImgUrl())
                .menuType(menu.getMenuType())
                .feedback(menu.getFeedback())
                .build();

        Menu savedMenu = menuRepository.save(updatedMenu);
        return mapToMenuResponse(savedMenu);
    }

    @Override
    public List<MenuResponse> getTodayMenus(String weekday) {
        LocalDate today = LocalDate.now();

        if (weekday == null || weekday.isEmpty()) {
            // 요일 지정이 없으면 오늘 요일로 설정
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            weekday = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.KOREAN);
        }

        // 해당 요일과 오늘 날짜에 맞는 메뉴 조회
        List<Menu> menus = menuRepository.findByWeekdayAndMenuDate(weekday, today);
        return menus.stream()
                .map(this::mapToMenuResponse)
                .sorted(Comparator.comparing(MenuResponse::getMenuLiked).reversed()) // 선호도 내림차순 정렬
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
                .menuDate(menu.getMenuDate())
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
                .menuDate(menu.getMenuDate())
                .build();
    }
}
