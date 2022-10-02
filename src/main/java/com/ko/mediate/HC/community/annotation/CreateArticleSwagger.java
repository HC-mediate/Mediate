package com.ko.mediate.HC.community.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "커뮤니티 글 생성 api", description = "dto = 글 제목, 내용, 카테고리를 포함하는 json, images = 글에 포함할 이미지, 여러 개를 올린다면 같은 이름으로 여러 파일을 올려주세요")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "201", description = "생성 성공"),
                @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰이거나 값이 없습니다."),
        })
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateArticleSwagger {
}
