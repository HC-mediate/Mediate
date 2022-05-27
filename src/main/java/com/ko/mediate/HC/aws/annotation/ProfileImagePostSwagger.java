package com.ko.mediate.HC.aws.annotation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiOperation(value = "프로필 이미지 생성")
@ApiResponses(
    value = {
      @ApiResponse(code = 201, message = "프로필 이미지 생성 성공"),
      @ApiResponse(code = 400, message = "유효하지 않은 이미지입니다."),
      @ApiResponse(code = 403, message = "인가되지 않은 자원입니다."),
      @ApiResponse(code = 401, message = "유효하지 않은 토큰이거나 값이 없습니다."),
      @ApiResponse(code = 404, message = "이미지 파일을 찾을 수 없습니다."),
      @ApiResponse(code = 415, message = "지원하지 않는 Media Type입니다.")
    })
public @interface ProfileImagePostSwagger {}
