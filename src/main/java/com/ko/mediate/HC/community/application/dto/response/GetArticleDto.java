package com.ko.mediate.HC.community.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ko.mediate.HC.community.domain.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;

@Builder
@Getter
public class GetArticleDto {

    @Schema(description = "글 ID")
    private Long articleId;
    @Schema(description = "작성자 이름")
    private String authorNickname;
    @Schema(description = "프로필 사진 URL")
    private String profileImageUrl;
    @Schema(description = "작성일")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createAt;
    @Schema(description = "좋아요 수")
    private Long like;
    @Schema(description = "조회 수")
    private Long view;
    @Schema(description = "카테고리")
    private String category;

    private static String getUrlIfNotNull(Article article) {
        return Objects.nonNull(article.getAccount().getProfileImage()) ? article.getAccount()
                .getProfileImage().getProfileUrl() : null;
    }

    static GetArticleDto fromEntity(Article article) {
        return GetArticleDto.builder()
                .articleId(article.getId())
                .authorNickname(article.getAccount().getNickname())
                .profileImageUrl(getUrlIfNotNull(article))
                .createAt(article.getCreateAt())
                .like(article.getLikeCount())
                .view(article.getViewCount())
                .category(article.getCategory().name())
                .build();
    }
}
