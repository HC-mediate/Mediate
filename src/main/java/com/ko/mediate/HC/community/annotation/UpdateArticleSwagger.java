package com.ko.mediate.HC.community.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Operation(summary = "커뮤니티 글 수정 api", description = "이미지는 수정할 수 없으며, 제목, 내용, 카테고리만 수정 가능합니다.")
@ApiResponses(
        value = {
                @ApiResponse(responseCode = "200", description = "수정 성공"),
                @ApiResponse(responseCode = "400", description = "잘못된 요청입니다."),
                @ApiResponse(responseCode = "401", description = "유효하지 않은 토큰이거나 값이 없습니다."),
        })
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UpdateArticleSwagger {

}
