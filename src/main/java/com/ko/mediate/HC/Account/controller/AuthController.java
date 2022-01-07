package com.ko.mediate.HC.Account.controller;

import com.ko.mediate.HC.Account.application.AccountJoinService;
import com.ko.mediate.HC.Account.application.dto.request.SignupDto;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AuthController {
  private final AccountJoinService joinService;

  @PostMapping(value = "/signup")
  public ResponseEntity<String> Signup(@Valid @RequestBody SignupDto dto) {
    joinService.Join(dto);
    return ResponseEntity.ok("회원가입이 완료되었습니다.");
  }
}
