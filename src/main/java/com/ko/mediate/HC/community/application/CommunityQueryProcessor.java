package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDetailDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleImageDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleListDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommunityQueryProcessor {
  private final JpaArticleRepository articleRepository;

  public GetArticleListDto getAllArticle(int page, int size) {
    Page<Article> queryResult =
        articleRepository.findAll(PageRequest.of(page, size, Sort.by("createAt").descending()));
    List<GetArticleDto> getArticleDtos =
        queryResult.getContent().stream().map(GetArticleDto::new).collect(Collectors.toList());
    return new GetArticleListDto(getArticleDtos, getArticleDtos.size() == 0);
  }

  public GetArticleDetailDto getArticleDetailById(Long id) {
    return new GetArticleDetailDto(
        articleRepository
            .findById(id)
            .orElseThrow(() -> new MediateNotFoundException("ID를 찾을 수 없습니다.")));
  }
}
