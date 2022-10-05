package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.domain.Account;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.domain.ArticleImageStorage;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.common.exception.MediateUnAuthorizedException;
import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDetailDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleListDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityService {

    private final JpaArticleRepository articleRepository;
    private final ArticleImageStorage articleImageStorage;
    private final AccountService accountService;

    @Transactional
    public Long createArticle(UserInfo userInfo, CreateArticleDto dto, List<MultipartFile> images)
            throws IOException {
        Account account = accountService.getAccountByEmail(userInfo.getAccountEmail());
        Article article = Article.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .category(dto.getCategory())
                .account(account)
                .build();
        if (Objects.nonNull(images) && images.size() > 0) {
            articleImageStorage.uploadImages(images).stream()
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

    @Transactional(readOnly = true)
    public GetArticleListDto getAllArticles(ArticleSearchCondition searchCondition) {
        return GetArticleListDto.fromEntities(
                articleRepository.findAllBySearchCondition(searchCondition));
    }

    @Transactional(readOnly = true)
    public GetArticleDetailDto getArticleDetailById(Long articleId) {
        return GetArticleDetailDto.fromEntity(articleRepository.findArticleByIdWithActive(articleId)
                .orElseThrow(MediateNotFoundException::new));
    }

    private void removeIfAttachedImages(Article article) {
        if (Objects.isNull(article.getArticleImageList())
                || article.getArticleImageList().size() == 0) {
            return;
        }
        articleImageStorage.deleteImages(article.getArticleImageList().stream()
                .map(i -> i.getAttachedImage()).collect(Collectors.toList()));
    }
}
