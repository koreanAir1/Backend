package com.korean.koreanairDiet.domain.menu.service;

import com.korean.koreanairDiet.domain.menu.dto.request.CommonMenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuResponse;
import com.korean.koreanairDiet.domain.menu.entity.CommonMenu;
import com.korean.koreanairDiet.domain.menu.repository.CommonMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<CommonMenuResponse> getAllCommonMenus() {
        List<CommonMenu> commonMenus = commonMenuRepository.findAll();
        return commonMenus.stream()
                .map(this::mapToCommonMenuResponse)
                .collect(Collectors.toList());
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

    private CommonMenuResponse mapToCommonMenuResponse(CommonMenu commonMenu) {
        return CommonMenuResponse.builder()
                .idCommon(commonMenu.getIdCommon())
                .commonName(commonMenu.getCommonName())
                .weekday(commonMenu.getWeekday())
                .build();
    }
}
