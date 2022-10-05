package com.ko.mediate.HC.community.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.ko.mediate.HC.community.domain.Article;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class GetArticleDetailDto {

    @Schema(description = "글 제목")
    private String title;
    @Schema(description = "글 내용")
    private String content;
    @Schema(description = "작성 일자")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime createAt;
    @Schema(description = "작성자 닉네임")
    private String authorNickname;
    @Schema(description = "좋아요 수")
    private Long like;
    @Schema(description = "이미지 URL 리스트")
    private List<String> images;

    public static GetArticleDetailDto fromEntity(Article article) {
        return GetArticleDetailDto.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .createAt(article.getCreateAt())
                .authorNickname(article.getAccount().getNickname())
                .like(article.getLikeCount())
                .images(article.getArticleImageList().stream()
                        .map(image -> image.getAttachedImage().getImageUrl())
                        .collect(Collectors.toList()))
                .build();
    }
}
