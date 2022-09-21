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
@ApiOperation(value = "로그인", notes = "성공 시 Authorization 헤더에 토큰 값을 반환")
@ApiResponses(
        value = {
                @ApiResponse(code = 201, message = "로그인 성공"),
                @ApiResponse(code = 400, message = "파라미터가 올바르지 않습니다."),
                @ApiResponse(code = 500, message = "서버 내부 에러")
        })
public @interface SignInSwagger {
}
