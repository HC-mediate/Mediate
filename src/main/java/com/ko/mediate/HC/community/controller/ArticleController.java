package com.ko.mediate.HC.community.controller;

import com.ko.mediate.HC.auth.annotation.TokenAccount;
import com.ko.mediate.HC.auth.resolver.TokenAccountInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.community.application.CommunityCommandExecutor;
import com.ko.mediate.HC.community.application.CommunityQueryProcessor;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDetailDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleListDto;
import com.ko.mediate.HC.community.application.dto.response.GetPopularArticleDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
// todo: 글 수정 API
// todo: 인기글 캐싱
// todo: 방문마다 조회수 +1
// todo: 좋아요 +1
// todo: 북마크, 댓글, 카테고리
public class ArticleController {

  private final CommunityCommandExecutor communityCommandExecutor;
  private final CommunityQueryProcessor communityQueryProcessor;

  @PostMapping(
      value = "/articles",
      consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
  public ResponseEntity<CommonResponseDto> createArticle(
      @TokenAccount TokenAccountInfo token,
      @RequestPart(value = "dto") RequestArticleDto dto,
      @RequestPart(value = "imgFile", required = false) MultipartFile[] multipartFiles) {
    communityCommandExecutor.createArticle(token, dto, multipartFiles);
    return ResponseEntity.ok(new CommonResponseDto("글을 작성했습니다."));
  }

  @GetMapping("/articles")
  public ResponseEntity<GetArticleListDto> getAllArticle(
      @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "0") int page) {
    return ResponseEntity.ok(communityQueryProcessor.getAllArticle(page, size));
  }

  @GetMapping("/articles/{id}")
  public ResponseEntity<GetArticleDetailDto> getArticleDetailById(@PathVariable long id) {
    return ResponseEntity.ok(communityQueryProcessor.getArticleDetailById(id));
  }

  @DeleteMapping("/articles/{id}")
  public ResponseEntity<CommonResponseDto> deleteArticleById(
      @TokenAccount TokenAccountInfo token, @PathVariable long id) {
    communityCommandExecutor.deleteArticle(token, id);
    return ResponseEntity.ok(new CommonResponseDto("글을 삭제했습니다."));
  }

  @GetMapping("/popular-articles")
  public ResponseEntity<GetPopularArticleDto> getPopularArticle() {
    return ResponseEntity.ok(communityQueryProcessor.getPopularArticle());
  }
}
