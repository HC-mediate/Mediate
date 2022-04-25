package com.ko.mediate.HC.community.application.dto.response;

import com.ko.mediate.HC.community.domain.ArticleImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleImageDto {
  private Long imageId;
  private String accessPath;

  public GetArticleImageDto(ArticleImage articleImage) {
    this.imageId = articleImage.getId();
    this.accessPath = articleImage.getAccessPath();
  }
}
