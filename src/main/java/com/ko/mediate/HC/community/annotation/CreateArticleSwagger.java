package com.ko.mediate.HC.community.annotation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "커뮤니티 글 생성 api")
@ApiResponses(
        value = {
                @ApiResponse(code = 201, message = "생성 성공"),
                @ApiResponse(code = 400, message = "잘못된 요청입니다."),
                @ApiResponse(code = 401, message = "유효하지 않은 토큰이거나 값이 없습니다."),
        })
public @interface CreateArticleSwagger {
}
