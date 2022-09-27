package com.ko.mediate.HC.community.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.community.annotation.CreateArticleSwagger;
import com.ko.mediate.HC.community.application.CommunityService;
import com.ko.mediate.HC.community.application.dto.request.RequestArticleDto;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ArticleController {

    private final CommunityService communityService;

    @PostMapping("/articles")
    @CreateArticleSwagger
    public ResponseEntity createArticle(@ApiParam(hidden = true) @LoginUser UserInfo userInfo,
                                        @Valid  @ModelAttribute RequestArticleDto dto) throws IOException {
        communityService.createArticle(userInfo, dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
