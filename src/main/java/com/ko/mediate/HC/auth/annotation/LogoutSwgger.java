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
@ApiOperation(value = "로그아웃", notes = "성공 시 서버에 저장된 액세스, 리프레쉬 토큰을 제거")
@ApiResponses(
    value = {
      @ApiResponse(code = 204, message = "로그아웃 성공"),
      @ApiResponse(code = 401, message = "토큰이 비어있거나 유효하지 않습니다."),
      @ApiResponse(code = 403, message = "이미 로그아웃되어 무효화 토큰으로 시도"),
      @ApiResponse(code = 500, message = "서버 내부 에러")
    })
public @interface LogoutSwgger {}
