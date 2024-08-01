package com.cj.product.core.response;

import com.cj.product.core.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class CommonControllerAdvice {

    /**
     * 시스템 예외 상황. HTTP 500 상태 코드와 FAIL 결과 반환
     * 집중 모니터링 대상
     *
     * @param e 예외 객체
     * @return 공통 응답 객체
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e) {
        log.error("[Exception] cause = {}, errorMsg = {}",
                NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
        return CommonResponse.fail(ErrorCode.COMMON_SYSTEM_ERROR);
    }

    /**
     * 비즈니스 로직 예외 처리. HTTP 200 상태 코드와 FAIL 결과 반환
     *
     * @param e BaseException 객체
     * @return 공통 응답 객체
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public CommonResponse handleBaseException(BaseException e) {
        log.warn("[BaseException] errorCode = {}, errorMsg = {}",
                e.getErrorCode(), e.getMessage());
        return CommonResponse.fail(e.getErrorCode().getErrorCode(), e.getMessage());
    }

    /**
     * 유효하지 않은 매개변수 예외 처리. HTTP 200 상태 코드와 FAIL 결과 반환
     *
     * @param e MethodArgumentNotValidException 객체
     * @return 공통 응답 객체
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.warn("[MethodArgumentNotValidException] errorMsg = {}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        String message = getFieldErrorMessage(bindingResult);
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
    }

    /**
     * 제약 조건 위반 예외 처리. HTTP 200 상태 코드와 FAIL 결과 반환
     *
     * @param e ConstraintViolationException 객체
     * @return 공통 응답 객체
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResponse handleConstraintViolationException(ConstraintViolationException e) {
        log.warn("[ConstraintViolationException] errorMsg = {}", e.getMessage());
        String message = e.getMessage() != null ? e.getMessage() : ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg();
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
    }

    /**
     * 요청 파라미터 누락 예외 처리. HTTP 200 상태 코드와 FAIL 결과 반환
     *
     * @param e MissingServletRequestParameterException 객체
     * @return 공통 응답 객체
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public CommonResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.warn("[MissingServletRequestParameterException] errorMsg = {}", e.getMessage());
        String message = String.format("Request Error: 요청 파라미터 (%s)가 없습니다.", e.getParameterName());
        return CommonResponse.fail(ErrorCode.COMMON_INVALID_PARAMETER.getErrorCode(), message);
    }

    /**
     * BindingResult에서 FieldError 메시지 가져오기
     * @param bindingResult BindingResult 객체
     * @return 에러 메시지
     */
    private String getFieldErrorMessage(BindingResult bindingResult) {
        FieldError fe = bindingResult.getFieldError();
        if (fe != null) {
            return String.format("Request Error: %s = %s (%s)", fe.getField(), fe.getRejectedValue(), fe.getDefaultMessage());
        }
        return ErrorCode.COMMON_INVALID_PARAMETER.getErrorMsg();
    }
}