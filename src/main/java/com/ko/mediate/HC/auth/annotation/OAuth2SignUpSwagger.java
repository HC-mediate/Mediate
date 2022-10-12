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
@ApiOperation(value = "소셜 로그인 회원가입", notes = "요청 시 이메일 중복확인")
@ApiResponses(
        value = {
                @ApiResponse(code = 201, message = "회원가입 성공"),
                @ApiResponse(code = 400, message = "이메일 중복 혹은 파라미터가 잘못되었습니다."),
                @ApiResponse(code = 500, message = "서버 내부 에러")
        })
public @interface OAuth2SignUpSwagger {

}
