package com.korean.koreanairDiet.domain.menu.repository;

import com.korean.koreanairDiet.domain.menu.entity.CommonMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommonMenuRepository extends JpaRepository<CommonMenu, Long> {

    // 요일별 공통 메뉴 리스트 조회
    List<CommonMenu> findByWeekday(String weekday);
}