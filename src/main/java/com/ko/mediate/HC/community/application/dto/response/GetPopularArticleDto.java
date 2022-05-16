package com.ko.mediate.HC.community.application.dto.response;

import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.domain.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPopularArticleDto {
  private Category category;
  private GetArticleDto articleDto;

  public GetPopularArticleDto(Category category, Article article){
    this.category = category;
    this.articleDto = new GetArticleDto(article);
  }

  public GetPopularArticleDto(
      Category category,
      long articleId,
      String title,
      String content,
      LocalDateTime createAt,
      long like,
      long view) {
    this.category = category;
    this.articleDto = new GetArticleDto(articleId, title, content, createAt, like, view);
  }
}
