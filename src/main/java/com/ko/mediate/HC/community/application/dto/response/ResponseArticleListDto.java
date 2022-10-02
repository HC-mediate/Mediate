package com.ko.mediate.HC.community.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleListDto {
    @Schema(description = "글 ID")
    private Long articleId;
    @Schema(description = "작성자 이름")
    private String authorNickname;
    @Schema(description = "프로필 사진 URL")
    private String profileImageUrl;
    @Schema(description = "작성일")
    private LocalDateTime createAt;
    @Schema(description = "좋아요 수")
    private Long like;
    @Schema(description = "조회 수")
    private Long view;
    @Schema(description = "카테고리")
    private String category;
}
