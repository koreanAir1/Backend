package com.korean.koreanairDiet.global.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseForm {
    private String code;
    private String message;
    private Object data;

    public static ResponseForm of(ResponseCode responseCode, Object data) {
        return ResponseForm.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .data(data)
                .build();
    }

    public static ResponseForm of(ResponseCode responseCode) {
        return ResponseForm.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();
    }
}