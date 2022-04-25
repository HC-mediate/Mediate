package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.common.exception.MediateFileExceedLimitException;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.common.infra.S3Uploader;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.ArticleImage;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        .orElseThrow(() -> new MediateNotFoundException("ID가 없습니다."));
  }

  @Transactional
  public void createArticle(
      TokenAccountInfo token, RequestArticleDto dto, MultipartFile[] multipartFiles)
      throws IOException {
    if (multipartFiles.length > 5) {
      throw new MediateFileExceedLimitException("이미지는 최대 5개까지 첨부 가능합니다.");
    }
    List<ArticleImage> articleImageList = new ArrayList<>();
    for (MultipartFile file : multipartFiles) {
      String accessPath = s3Uploader.upload(file);
      log.info("article title: {}, file upload url:{}", dto.getTitle(), accessPath);
      ArticleImage articleImage = new ArticleImage(accessPath, file.getOriginalFilename());
      articleImageList.add(articleImage);
    }
    Article article =
        Article.builder()
            .title(dto.getTitle())
            .content(dto.getContent())
            .writeBy(token.getAccountId())
            .articleImageList(articleImageList)
            .build();
    articleRepository.save(article);
  }

  @Transactional
  public void deleteArticle(TokenAccountInfo token, Long id) {
    Article article = findByIdAndWriteBy(id, token.getAccountId());
    articleRepository.delete(article);
  }
}
