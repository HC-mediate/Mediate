package com.ko.mediate.HC.community.application;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleSearchCondition {

    private String keyword;
    private Long lastArticleId;
    private Integer limit;
    private ArticleSort sort = ArticleSort.LATEST;
}
