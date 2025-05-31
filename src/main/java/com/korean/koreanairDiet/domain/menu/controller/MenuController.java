package com.korean.koreanairDiet.domain.menu.controller;

import com.korean.koreanairDiet.domain.menu.dto.request.MenuLikeRequest;
import com.korean.koreanairDiet.domain.menu.dto.request.MenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.MenuWeeklyResponse;
import com.korean.koreanairDiet.domain.menu.service.MenuService;
import com.korean.koreanairDiet.global.response.ResponseCode;
import com.korean.koreanairDiet.global.response.ResponseForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "메뉴 API", description = "식단 메뉴 관련 API")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/menu")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "메뉴 상세 조회", description = "특정 ID의 메뉴 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 메뉴를 찾을 수 없음")
    @GetMapping("/{menuId}")
    public ResponseEntity<ResponseForm> getMenu(@PathVariable Long menuId) {
        MenuResponse response = menuService.getMenuById(menuId);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_GET_SUCCESS, response));
    }

    @Operation(summary = "주간 메뉴 조회", description = "요일별 식단 메뉴를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 주간 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = MenuWeeklyResponse.class)))
    @GetMapping("/weekly")
    public ResponseEntity<ResponseForm> getWeeklyMenus() {
        List<MenuWeeklyResponse> responses = menuService.getWeeklyMenus();
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_WEEK_SUCCESS, responses));
    }

    @Operation(summary = "오늘의 메뉴 조회", description = "오늘 제공되는 모든 메뉴를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 오늘의 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @GetMapping("/today")
    public ResponseEntity<ResponseForm> getTodayMenus(@RequestParam(required = false) String weekday) {
            List<MenuResponse> responses = menuService.getTodayMenus(weekday);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_LIST_SUCCESS, responses));
    }

    @Operation(summary = "메뉴 추가", description = "새로운 메뉴를 추가합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 메뉴 추가 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청")
    @PostMapping
    public ResponseEntity<ResponseForm> addMenu(@RequestBody MenuRequest request) {
        MenuResponse response = menuService.addMenu(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_ADD_SUCCESS, response));
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 메뉴 수정 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 메뉴를 찾을 수 없음")
    @PutMapping("/{menuId}")
    public ResponseEntity<ResponseForm> updateMenu(@PathVariable Long menuId, @RequestBody MenuRequest request) {
        MenuResponse response = menuService.updateMenu(menuId, request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_UPDATE_SUCCESS, response));
    }

    @Operation(summary = "메뉴 삭제", description = "메뉴를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 메뉴 삭제 성공")
    @ApiResponse(responseCode = "404", description = "실패 - 메뉴를 찾을 수 없음")
    @DeleteMapping("/{menuId}")
    public ResponseEntity<ResponseForm> deleteMenu(@PathVariable Long menuId) {
        menuService.deleteMenu(menuId);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_DELETE_SUCCESS));
    }

    @Operation(summary = "메뉴 선호도 증가", description = "특정 메뉴에 선호도를 1 증가시킵니다")
    @ApiResponse(responseCode = "200", description = "성공 - 선호도 추가 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 메뉴를 찾을 수 없음")
    @PostMapping("/like")
    public ResponseEntity<ResponseForm> likeMenu(@RequestBody MenuLikeRequest request) {
        MenuResponse response = menuService.likeMenu(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_LIKE_SUCCESS, response));
    }

    @Operation(summary = "메뉴 선호도 감소", description = "특정 메뉴에 선호도를 1 감소시킵니다")
    @ApiResponse(responseCode = "200", description = "성공 - 선호도 감소 성공", content = @Content(schema = @Schema(implementation = MenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 메뉴를 찾을 수 없음")
    @PostMapping("/dislike")
    public ResponseEntity<ResponseForm> dislikeMenu(@RequestBody MenuLikeRequest request) {
        MenuResponse response = menuService.dislikeMenu(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_LIKE_SUCCESS, response));
    }
}