package com.ko.mediate.HC.community.application;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDetailDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleListDto;
import com.ko.mediate.HC.community.application.dto.response.GetPopularArticleDto;
import com.ko.mediate.HC.community.domain.Article;
import com.ko.mediate.HC.community.infra.JpaArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    @Cacheable(value = "articleDetail", key = "#id")
    public GetArticleDetailDto getArticleDetailById(Long id) {
        return new GetArticleDetailDto(
                articleRepository
                        .findByIdWithImages(id)
                        .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND)));
    }

    public GetPopularArticleDto getPopularArticle() {
        return articleRepository
                .findPopularArticleByCategory()
                .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
    }
}
