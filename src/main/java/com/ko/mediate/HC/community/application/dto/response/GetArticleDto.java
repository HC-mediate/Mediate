package com.ko.mediate.HC.community.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ko.mediate.HC.community.domain.Article;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetArticleDto {
  private long articleId;
  private String title;
  private String content;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss",
      timezone = "Asia/Seoul")
  private LocalDateTime createAt;

  private long like;
  private long view;

  public GetArticleDto(Article article) {
    this.articleId = article.getId();
    this.title = article.getTitle();
    this.content = article.getContent();
    this.createAt = article.getCreateAt();
    this.like = article.getLike();
    this.view = article.getView();
  }
}
