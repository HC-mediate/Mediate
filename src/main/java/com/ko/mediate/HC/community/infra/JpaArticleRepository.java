package com.ko.mediate.HC.community.infra;

import com.ko.mediate.HC.community.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaArticleRepository extends JpaRepository<Article, Long> {
}
