package com.ko.mediate.HC.community.infra;

import com.ko.mediate.HC.community.application.ArticleSearchCondition;
import com.ko.mediate.HC.community.application.ArticleSort;
import com.ko.mediate.HC.community.domain.Article;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.ko.mediate.HC.auth.domain.QAccount.account;
import static com.ko.mediate.HC.community.domain.QArticle.article;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JpaSearchArticleRepositoryImpl implements JpaSearchArticleRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Article> findAllBySearchCondition(ArticleSearchCondition searchCondition) {
        List<Article> contents = queryFactory.selectFrom(article)
                .innerJoin(article.account, account)
                .on(article.account.id.eq(account.id))
                .where(account.isActivated.isTrue(),
                        ltArticleId(searchCondition.getLastArticleId()),
                        containsTitle(searchCondition),
                        containsContent(searchCondition))
                .orderBy(articleSortList(searchCondition).stream().toArray(OrderSpecifier[]::new))
                .limit(searchCondition.getLimit())
                .fetch();
        boolean hasNext = false;
        if (contents.size() > searchCondition.getLimit()) {
            hasNext = true;
            contents.remove(searchCondition.getLimit());
        }
        return new SliceImpl<>(contents, Pageable.ofSize(searchCondition.getLimit()), hasNext);
    }

    private BooleanExpression containsTitle(ArticleSearchCondition searchCondition) {
        return StringUtils.isNotBlank(searchCondition.getKeyword()) ? article.title.contains(searchCondition.getKeyword()) : null;
    }

    private BooleanExpression containsContent(ArticleSearchCondition searchCondition) {
        return StringUtils.isNotBlank(searchCondition.getKeyword()) ? article.content.contains(searchCondition.getKeyword()) : null;
    }

    private BooleanExpression ltArticleId(Long articleId) {
        if (Objects.isNull(articleId) || articleId == 0) {
            return null;
        }
        return article.id.lt(articleId);
    }

    private List<OrderSpecifier> articleSortList(ArticleSearchCondition searchCondition) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if(searchCondition.getSort() == ArticleSort.LATEST){
            orders.add(article.createAt.desc());
        }
        else{
            orders.add(article.likeCount.desc());
        }
        return orders;
    }
}
