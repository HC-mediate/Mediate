package com.ko.mediate.HC.auth.annotation;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value = "토큰 재발급", notes = "성공 시 액세스 토큰 재발급. 단, 무효화되지 않은 액세스 토큰이 이미 있다면 해당 토큰을 재사용")
@ApiResponses(
    value = {
      @ApiResponse(code = 201, message = "토큰 재발급 성공"),
      @ApiResponse(code = 401, message = "토큰이 없거나 유효하지 않음"),
      @ApiResponse(code = 403, message = "로그아웃되어 무효화된 토큰으로 시도"),
      @ApiResponse(code = 500, message = "서버 내부 에러")
    })
@ApiImplicitParams(
  @ApiImplicitParam(value = "리프레쉬 토큰 값", name = "Refresh", required = true)
)
public @interface ReissueTokenSwagger {}
