package com.ko.mediate.HC.community.infra;

import com.ko.mediate.HC.community.domain.Article;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleRepository extends JpaRepository<Article, Long>, JpaSearchArticleRepository {
    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.articleImageList WHERE a.id = :articleId")
    Optional<Article> findArticleByIdFetch(@Param("articleId") Long articleId);
}
