package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.CommonMenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuWeeklyResponse;
import com.korean.koreanairDiet.domain.menu.entity.CommonMenu;
import com.korean.koreanairDiet.domain.menu.repository.CommonMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommonMenuServiceImpl implements CommonMenuService {
    private final CommonMenuRepository commonMenuRepository;

    @Override
    public CommonMenuResponse getCommonMenuById(Long commonMenuId) {
        CommonMenu commonMenu = commonMenuRepository.findById(commonMenuId)
                .orElseThrow(() -> new RuntimeException("공통 메뉴를 찾을 수 없습니다."));
        return mapToCommonMenuResponse(commonMenu);
    }

    @Override
    public List<CommonMenuResponse> getCommonMenusByWeekday(String weekday) {
        List<CommonMenu> commonMenus = commonMenuRepository.findByWeekday(weekday);
        return commonMenus.stream()
                .map(this::mapToCommonMenuResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CommonMenuResponse addCommonMenu(CommonMenuRequest request) {
        CommonMenu commonMenu = CommonMenu.builder()
                .commonName(request.getCommonName())
                .weekday(request.getWeekday())
                .build();

        CommonMenu savedCommonMenu = commonMenuRepository.save(commonMenu);
        return mapToCommonMenuResponse(savedCommonMenu);
    }

    @Override
    @Transactional
    public CommonMenuResponse updateCommonMenu(Long commonMenuId, CommonMenuRequest request) {
        CommonMenu existingCommonMenu = commonMenuRepository.findById(commonMenuId)
                .orElseThrow(() -> new RuntimeException("공통 메뉴를 찾을 수 없습니다."));

        CommonMenu updatedCommonMenu = CommonMenu.builder()
                .idCommon(existingCommonMenu.getIdCommon())
                .commonName(request.getCommonName())
                .weekday(request.getWeekday())
                .build();

        CommonMenu savedCommonMenu = commonMenuRepository.save(updatedCommonMenu);
        return mapToCommonMenuResponse(savedCommonMenu);
    }

    @Override
    @Transactional
    public void deleteCommonMenu(Long commonMenuId) {
        if (!commonMenuRepository.existsById(commonMenuId)) {
            throw new RuntimeException("공통 메뉴를 찾을 수 없습니다.");
        }
        commonMenuRepository.deleteById(commonMenuId);
    }

    @Override
    public List<CommonMenuWeeklyResponse> getWeeklyCommonMenus() {
        List<String> weekdays = Arrays.asList("월요일", "화요일", "수요일", "목요일", "금요일");

        return weekdays.stream()
                .map(weekday -> {
                    // 해당 요일의 공통 메뉴 조회
                    List<CommonMenu> commonMenus = commonMenuRepository.findByWeekday(weekday);
                    List<CommonMenuResponse> commonMenuResponses = commonMenus.stream()
                            .map(this::mapToCommonMenuResponse)
                            .collect(Collectors.toList());

                    // 응답 객체 생성
                    return CommonMenuWeeklyResponse.builder()
                            .weekday(weekday)
                            .commonMenus(commonMenuResponses)
                            .build();
                })
                .collect(Collectors.toList());
    }

    private CommonMenuResponse mapToCommonMenuResponse(CommonMenu commonMenu) {
        return CommonMenuResponse.builder()
                .idCommon(commonMenu.getIdCommon())
                .commonName(commonMenu.getCommonName())
                .weekday(commonMenu.getWeekday())
                .build();
    }
}
