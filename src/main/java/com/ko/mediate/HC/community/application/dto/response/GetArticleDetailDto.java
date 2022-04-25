package com.ko.mediate.HC.community.application.dto.response;

import com.ko.mediate.HC.community.domain.Article;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleDetailDto {
  private GetArticleDto content;
  private List<GetArticleImageDto> images;

  public GetArticleDetailDto(Article article) {
    this.content = new GetArticleDto(article);
    this.images =
        article.getArticleImageList().stream()
            .map(GetArticleImageDto::new)
            .collect(Collectors.toList());
  }
}
