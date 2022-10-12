package com.ko.mediate.HC.aws.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.annotation.ProfileImagePostSwagger;
import com.ko.mediate.HC.aws.application.ProfileImageService;
import com.ko.mediate.HC.aws.application.dto.request.ProfileImageRequestDto;
import com.ko.mediate.HC.aws.application.dto.response.ProfileImageResponseDto;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Api(tags = "프로필 이미지 업로드")
public class ProfileImageController {

    private final ProfileImageService profileImageService;

    @ProfileImagePostSwagger
    @PostMapping("/profile-image")
    public ResponseEntity<ProfileImageResponseDto> uploadProfileImage(
            @ApiIgnore @LoginUser UserInfo userInfo, @ModelAttribute ProfileImageRequestDto dto)
            throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileImageService.uploadProfileImage(userInfo, dto));
    }
}
