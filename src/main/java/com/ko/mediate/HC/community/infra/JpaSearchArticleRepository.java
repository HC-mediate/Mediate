package com.ko.mediate.HC.community.infra;

import com.ko.mediate.HC.community.application.ArticleSearchCondition;
import com.ko.mediate.HC.community.domain.Article;
import org.springframework.data.domain.Slice;

public interface JpaSearchArticleRepository {
    Slice<Article> findAllBySearchCondition(ArticleSearchCondition searchCondition);
}
