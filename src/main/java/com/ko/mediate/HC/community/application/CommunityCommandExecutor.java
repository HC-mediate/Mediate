package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateFileExceedLimitException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.common.infra.S3Uploader;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityCommandExecutor {
  private final JpaArticleRepository articleRepository;
  private final S3Uploader s3Uploader;

  private Article findByIdAndWriteBy(Long id, String accountId) {
    return articleRepository
        .findByIdAndWriteBy(id, accountId)
        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
  }

  @Transactional
  public void createArticle(
      UserInfo userInfo, RequestArticleDto dto, MultipartFile[] multipartFiles) {
    if (multipartFiles != null && multipartFiles.length > 5) {
      throw new MediateFileExceedLimitException(ErrorCode.IMAGE_EXCEED);
    }
    Article article =
        Article.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .writeBy(userInfo.getAccountEmail())
            .category(dto.getCategory())
            .build();
    Optional.ofNullable(multipartFiles)
        .ifPresent(
            files -> {
              for (MultipartFile file : files) {
                String accessPath;
                try {
                  accessPath = s3Uploader.upload(file);
                  log.info("article title: {}, file upload url:{}", dto.getTitle(), accessPath);
                  ArticleImage articleImage =
                      new ArticleImage(accessPath, file.getOriginalFilename());
                  article.addArticleImage(articleImage);
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            });
    articleRepository.save(article);
  }

  @Transactional
  @CacheEvict(value = "articleDetail", key = "#id")
  public void deleteArticle(UserInfo userInfo, Long id) {
    Article article = findByIdAndWriteBy(id, userInfo.getAccountEmail());
    articleRepository.delete(article);
  }
}
