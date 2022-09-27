package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.domain.ArticleImageStorage;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final JpaArticleRepository articleRepository;
    private final ArticleImageStorage articleImageStorage;

    @Transactional
    public Long createArticle(UserInfo userInfo, RequestArticleDto dto) throws IOException {
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .authorEmail(userInfo.getAccountEmail())
                .authorName(userInfo.getAccountNickname())
                .build();
        if (Objects.nonNull(dto.getImages())) {
            articleImageStorage.uploadImages(dto.getImages()).stream()
                    .forEach(image -> article.addArticleImage(ArticleImage.builder()
                            .article(article).attachedImage(image).build()));
        }
        return articleRepository.save(article).getId();
    }
}
