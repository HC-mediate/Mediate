package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final JpaArticleRepository articleRepository;
}
