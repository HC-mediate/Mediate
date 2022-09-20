package com.ko.mediate.HC.auth.annotation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value = "마이페이지 보기", notes = "현재 로그인한 사용자에 따라 정보를 다르게 보여줍니다.")
@ApiResponses(
        value = {
                @ApiResponse(code = 200, message = "조회 성공"),
                @ApiResponse(code = 400, message = "잘못된 요청입니다."),
                @ApiResponse(code = 401, message = "유효하지 않은 토큰이거나 값이 없습니다."),
        })
public @interface GetAccountInfoSwagger {
}
