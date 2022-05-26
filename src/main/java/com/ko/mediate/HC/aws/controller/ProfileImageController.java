package com.ko.mediate.HC.aws.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.aws.application.ProfileImageService;
import com.ko.mediate.HC.aws.application.request.ProfileImageRequestDto;
import com.ko.mediate.HC.aws.application.response.ProfileImageResponseDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileImageController {

  private final ProfileImageService profileImageService;

  @PostMapping("/profile-image")
  public ResponseEntity<ProfileImageResponseDto> uploadProfileImage(
      @LoginUser UserInfo userInfo, @ModelAttribute ProfileImageRequestDto dto)
      throws IOException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(profileImageService.uploadProfileImage(userInfo, dto));
  }
}
