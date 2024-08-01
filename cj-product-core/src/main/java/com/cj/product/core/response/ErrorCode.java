package com.cj.product.core.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMMON_SYSTEM_ERROR(20000, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요."),
    COMMON_INVALID_PARAMETER(20001, "요청한 값이 올바르지 않습니다,"),
    COMMON_REDIS_LOCK_ERROR(20003, "일시적인 오류가 발생했습니다. 잠시 후 다시 시도해주세요. (code:20003)"),
    ERROR_OUT_OF_STOCK(30001, "상품의 재고가 부족합니다."),
    ERROR_PRODUCT_NOT_FOUND(30002, "해당 상품 정보가 없습니다."),
    ERROR_INVALID_QUANTITY(30003, "요청한 수량이 올바르지 않습니다."),
    ERROR_INVALID_DECREASE_QUANTITY(30004, "재고 차감 수량이 올바르지 않습니다.(max 초과)"),

    ;

    @Getter
    private final int errorCode;
    private final String errorMsg;

    public String getErrorMsg(Object... arg) {
        return String.format(errorMsg, arg);
    }
}
