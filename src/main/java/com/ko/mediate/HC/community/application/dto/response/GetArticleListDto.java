package com.ko.mediate.HC.community.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleListDto {
    private List<GetArticleDto> dtoList;
    private boolean hasNext;
}
