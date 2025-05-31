package com.korean.koreanairDiet.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {
    // 성공 코드
    MENU_GET_SUCCESS("M001", "메뉴 조회 성공"),
    MENU_LIST_SUCCESS("M002", "메뉴 목록 조회 성공"),
    MENU_WEEK_SUCCESS("M003", "주간 메뉴 조회 성공"),
    MENU_ADD_SUCCESS("M004", "메뉴 추가 성공"),
    MENU_UPDATE_SUCCESS("M005", "메뉴 수정 성공"),
    MENU_DELETE_SUCCESS("M006", "메뉴 삭제 성공"),
    MENU_LIKE_SUCCESS("M007", "메뉴 선호도 추가 성공"),
    MENU_LIKE_RANK_SUCCESS("M008", "메뉴 선호도 순위 조회 성공"),

    FEEDBACK_ADD_SUCCESS("F001", "피드백 추가 성공"),
    FEEDBACK_GET_SUCCESS("F002", "피드백 조회 성공"),
    FEEDBACK_STATS_SUCCESS("F003", "피드백 통계 조회 성공"),
    MENU_RECOMMENDATION_SUCCESS("MENU_RECOMMENDATION_SUCCESS", "식단 추천이 성공적으로 완료되었습니다."),



    // 실패 코드
    MENU_NOT_FOUND("E001", "메뉴를 찾을 수 없습니다"),
    FEEDBACK_NOT_FOUND("E002", "피드백 정보를 찾을 수 없습니다"),
    INVALID_REQUEST("E003", "잘못된 요청입니다"),
    SERVER_ERROR("E999", "서버 오류가 발생했습니다");

    private final String code;
    private final String message;
}