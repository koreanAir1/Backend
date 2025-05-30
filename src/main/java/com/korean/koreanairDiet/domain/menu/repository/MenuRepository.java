package com.korean.koreanairDiet.domain.menu.repository;

import com.korean.koreanairDiet.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByWeekday(String weekday);

    List<Menu> findByMenuType(String menuType);

    List<Menu> findByWeekdayAndMenuType(String weekday, String menuType);

    @Query("SELECT m FROM Menu m ORDER BY m.menuLiked DESC")
    List<Menu> findAllOrderByMenuLikedDesc();

    @Query("SELECT m FROM Menu m WHERE m.weekday = ?1 AND m.menuLine = ?2")
    List<Menu> findByWeekdayAndMenuLine(String weekday, String menuLine);
}
