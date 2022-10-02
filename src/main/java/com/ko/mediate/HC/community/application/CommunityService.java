package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.domain.ArticleImageStorage;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateUnAuthorizedException;
import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final JpaArticleRepository articleRepository;
    private final ArticleImageStorage articleImageStorage;

    @Transactional
    public Long createArticle(UserInfo userInfo, CreateArticleDto dto) throws IOException {
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

    @Transactional
    public void deleteArticle(UserInfo userInfo, Long articleId) {
        Article existingArticle = articleRepository.findArticleByIdFetch(articleId)
                .orElseThrow(() -> new MediateIllegalStateException(ErrorCode.ENTITY_NOT_FOUND));
        if (!existingArticle.isAuthorByEmail(userInfo.getAccountEmail())) {
            throw new MediateUnAuthorizedException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        removeIfAttachedImages(existingArticle);
        articleRepository.delete(existingArticle);
    }

    private void removeIfAttachedImages(Article article) {
        if (Objects.isNull(article.getArticleImageList()) || article.getArticleImageList().size() == 0) {
            return;
        }
        articleImageStorage.deleteImages(article.getArticleImageList().stream()
                .map(i -> i.getAttachedImage()).collect(Collectors.toList()));
    }
}
