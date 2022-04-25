package com.ko.mediate.HC.community.application.dto.request;

import com.ko.mediate.HC.community.domain.ArticleType;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestArticleDto {
  @NotEmpty(message = "글 제목은 반드시 있어야 합니다.")
  private String title;

  @NotEmpty(message = "글 내용은 반드시 있어야 합니다.")
  private String content;

  private ArticleType articleType;
}
