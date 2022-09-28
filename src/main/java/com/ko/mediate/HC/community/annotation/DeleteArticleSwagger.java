package com.ko.mediate.HC.community.annotation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApiOperation(value = "커뮤니티 글 생성 api")
@ApiResponses(
        value = {
                @ApiResponse(code = 204, message = "제거 성공"),
                @ApiResponse(code = 401, message = "유효하지 않은 토큰이거나 값이 없습니다."),
                @ApiResponse(code = 403, message = "작성자가 아닌 사용자가 글을 삭제하려 접근할 때 발생합니다.")
        })
public @interface DeleteArticleSwagger {
}
