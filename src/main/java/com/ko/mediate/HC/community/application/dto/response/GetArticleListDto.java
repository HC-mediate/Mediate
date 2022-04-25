package com.ko.mediate.HC.community.application.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleListDto {
  private List<GetArticleDto> dtoList;
  private boolean hasNext;
}
