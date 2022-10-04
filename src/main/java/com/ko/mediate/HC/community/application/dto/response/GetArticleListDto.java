package com.ko.mediate.HC.community.application.dto.response;

import com.ko.mediate.HC.community.domain.Article;
import lombok.*;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GetArticleListDto {
    List<GetArticleDto> contents;
    boolean hasNext;

    public static GetArticleListDto fromEntities(Slice<Article> articles) {
        return GetArticleListDto.builder()
                .contents(articles.stream().map(GetArticleDto::fromEntity).collect(Collectors.toList()))
                .hasNext(articles.hasNext())
                .build();
    }
}
