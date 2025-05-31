package com.korean.koreanairDiet.domain.menu.controller;

import com.korean.koreanairDiet.domain.menu.dto.request.CommonMenuRequest;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuResponse;
import com.korean.koreanairDiet.domain.menu.dto.response.CommonMenuWeeklyResponse;
import com.korean.koreanairDiet.domain.menu.service.CommonMenuService;
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
@Tag(name = "공통메뉴 API", description = "공통 제공 메뉴 관련 API")
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/common-menu")
public class CommonMenuController {
    private final CommonMenuService commonMenuService;

    @Operation(summary = "일주일 공통 메뉴 조회", description = "월요일부터 금요일까지의 공통 메뉴를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 일주일 공통 메뉴 조회 성공")
    @GetMapping("/weekly")
    public ResponseEntity<ResponseForm> getWeeklyCommonMenus() {
        List<CommonMenuWeeklyResponse> responses = commonMenuService.getWeeklyCommonMenus();
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_LIST_SUCCESS, responses));
    }

    @Operation(summary = "요일별 공통 메뉴 조회", description = "특정 요일의 공통 메뉴를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 요일별 공통 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = CommonMenuResponse.class)))
    @GetMapping("/by-weekday")
    public ResponseEntity<ResponseForm> getCommonMenusByWeekday(@RequestParam String weekday) {
        List<CommonMenuResponse> responses = commonMenuService.getCommonMenusByWeekday(weekday);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_LIST_SUCCESS, responses));
    }

    @Operation(summary = "공통 메뉴 상세 조회", description = "특정 ID의 공통 메뉴 정보를 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 공통 메뉴 조회 성공", content = @Content(schema = @Schema(implementation = CommonMenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 공통 메뉴를 찾을 수 없음")
    @GetMapping("/{commonMenuId}")
    public ResponseEntity<ResponseForm> getCommonMenu(@PathVariable Long commonMenuId) {
        CommonMenuResponse response = commonMenuService.getCommonMenuById(commonMenuId);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_GET_SUCCESS, response));
    }

    @Operation(summary = "공통 메뉴 추가", description = "새로운 공통 메뉴를 추가합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 공통 메뉴 추가 성공", content = @Content(schema = @Schema(implementation = CommonMenuResponse.class)))
    @ApiResponse(responseCode = "400", description = "실패 - 잘못된 요청")
    @PostMapping
    public ResponseEntity<ResponseForm> addCommonMenu(@RequestBody CommonMenuRequest request) {
        CommonMenuResponse response = commonMenuService.addCommonMenu(request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_ADD_SUCCESS, response));
    }

    @Operation(summary = "공통 메뉴 수정", description = "기존 공통 메뉴 정보를 수정합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 공통 메뉴 수정 성공", content = @Content(schema = @Schema(implementation = CommonMenuResponse.class)))
    @ApiResponse(responseCode = "404", description = "실패 - 공통 메뉴를 찾을 수 없음")
    @PutMapping("/{commonMenuId}")
    public ResponseEntity<ResponseForm> updateCommonMenu(@PathVariable Long commonMenuId, @RequestBody CommonMenuRequest request) {
        CommonMenuResponse response = commonMenuService.updateCommonMenu(commonMenuId, request);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_UPDATE_SUCCESS, response));
    }

    @Operation(summary = "공통 메뉴 삭제", description = "공통 메뉴를 삭제합니다")
    @ApiResponse(responseCode = "200", description = "성공 - 공통 메뉴 삭제 성공")
    @ApiResponse(responseCode = "404", description = "실패 - 공통 메뉴를 찾을 수 없음")
    @DeleteMapping("/{commonMenuId}")
    public ResponseEntity<ResponseForm> deleteCommonMenu(@PathVariable Long commonMenuId) {
        commonMenuService.deleteCommonMenu(commonMenuId);
        return ResponseEntity.ok(ResponseForm.of(ResponseCode.MENU_DELETE_SUCCESS));
    }
}