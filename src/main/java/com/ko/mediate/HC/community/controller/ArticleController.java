package com.ko.mediate.HC.community.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.community.annotation.CreateArticleSwagger;
import com.ko.mediate.HC.community.annotation.DeleteArticleSwagger;
import com.ko.mediate.HC.community.annotation.UpdateArticleSwagger;
import com.ko.mediate.HC.community.application.ArticleSearchCondition;
import com.ko.mediate.HC.community.application.ArticleSort;
import com.ko.mediate.HC.community.application.CommunityService;
import com.ko.mediate.HC.community.application.dto.request.CreateArticleDto;
import com.ko.mediate.HC.community.application.dto.request.UpdateArticleDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleDetailDto;
import com.ko.mediate.HC.community.application.dto.response.GetArticleListDto;
import io.swagger.annotations.ApiParam;
import java.io.IOException;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private final CommunityService communityService;

    @PostMapping(value = "/articles", consumes = {MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE})
    @CreateArticleSwagger
    public ResponseEntity<?> createArticle(@ApiIgnore @LoginUser UserInfo userInfo,
            @Valid @RequestPart CreateArticleDto dto,
            @RequestPart(required = false) List<MultipartFile> images)
            throws IOException {
        communityService.createArticle(userInfo, dto, images);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/articles/{articleId}")
    @DeleteArticleSwagger
    public ResponseEntity<?> deleteArticle(@ApiIgnore @LoginUser UserInfo userInfo,
            @ApiParam(value = "글 ID", required = true) @PathVariable Long articleId) {
        communityService.deleteArticle(userInfo, articleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/articles")
    public ResponseEntity<GetArticleListDto> getAllArticles(
            @RequestParam(defaultValue = "0") Long lastArticleId,
            @RequestParam(defaultValue = "5") Integer limit,
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "LATEST") String sort) {
        return ResponseEntity.ok(communityService.getAllArticles(ArticleSearchCondition.builder()
                .lastArticleId(lastArticleId)
                .limit(limit)
                .sort(ArticleSort.valueOf(sort))
                .keyword(keyword).build()));
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<GetArticleDetailDto> getArticleDetailById(@PathVariable Long articleId) {
        return ResponseEntity.ok(communityService.getArticleDetailById(articleId));
    }

    @PatchMapping("/articles/{articleId}")
    @UpdateArticleSwagger
    public ResponseEntity<GetArticleDetailDto> updateArticleById(@LoginUser UserInfo userInfo,
            @PathVariable Long articleId,
            @RequestBody UpdateArticleDto dto) {
        return ResponseEntity.ok(communityService.updateArticleById(userInfo, articleId, dto));
    }
}
