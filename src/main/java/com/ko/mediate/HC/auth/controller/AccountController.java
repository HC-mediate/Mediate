package com.ko.mediate.HC.auth.controller;

import com.ko.mediate.HC.auth.annotation.GetAccountInfoSwagger;
import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.response.GetAccountInfoDto;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "계정 정보 조회")
public class AccountController {
  private final AccountService accountService;

  @GetAccountInfoSwagger
  @GetMapping(value = "/mypage")
  public ResponseEntity<GetAccountInfoDto> getAccountInfo(@LoginUser UserInfo userInfo) {
    return ResponseEntity.ok(accountService.getAccountInfo(userInfo));
  }
}
