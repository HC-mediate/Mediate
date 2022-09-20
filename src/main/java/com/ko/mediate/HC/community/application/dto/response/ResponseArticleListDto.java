package com.ko.mediate.HC.community.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseArticleListDto {
    private Long articleId;
    private String authorName;
    private String profileImageUrl;
    private LocalDateTime createAt;
    private Long like;
    private Long view;
    private String category;
}
