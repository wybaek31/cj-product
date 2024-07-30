package com.cj.product.core.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(20000, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(20001, "요청한 값이 올바르지 않습니다,"),
    ERROR_OUT_OF_STOCK(30001, "상품의 재고가 부족합니다."),

    ;

    @Getter
    private final int errorCode;
    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}