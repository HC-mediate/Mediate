package com.ko.mediate.HC.auth.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.response.GetAccountInfoDto;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {
  private final AccountService accountService;

  @ApiOperation(value = "마이페이지", notes = "현재 로그인된 튜터/튜티마다 마이페이지를 다르게 보여줍니다.")
  @GetMapping(value = "/mypage")
  public ResponseEntity<GetAccountInfoDto> getAccountInfo(@LoginUser UserInfo userInfo) {
    return ResponseEntity.ok(accountService.getAccountInfo(userInfo));
  }
}
