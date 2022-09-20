package com.ko.mediate.HC.community.infra;

import com.ko.mediate.HC.community.application.dto.response.GetPopularArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findByIdAndWriteBy(Long id, String accountId);

    @Query("SELECT a FROM Article a LEFT JOIN FETCH a.articleImageList WHERE a.id = :id")
    Optional<Article> findByIdWithImages(@Param("id") Long id);

    @Query("SELECT new com.ko.mediate.HC.community.application.dto.response.GetPopularArticleDto(a.category, a.id, a.title, a.content, a.createAt, a.like, a.view) FROM Article a")
    Optional<GetPopularArticleDto> findPopularArticleByCategory();
}
