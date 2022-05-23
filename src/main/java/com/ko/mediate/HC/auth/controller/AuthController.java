package com.ko.mediate.HC.auth.controller;

import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.firebase.application.FirebaseCloudService;
import com.ko.mediate.HC.jwt.JwtFilter;
import com.ko.mediate.HC.tutoring.application.response.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"로그인용 api"})
public class AuthController {
  private final FirebaseCloudService firebaseCloudService;
  private final AccountService accountService;
  private final AuthService authService;

  @PostMapping("/sign-in")
  @ApiOperation(
      value = "로그인",
      notes = "성공시 jwt 토큰을 헤더와 응답 값에 넣어 반환합니다.",
      produces = "application/json",
      response = TokenDto.class)
  public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto dto) {

    TokenDto tokenDto = authService.signIn(dto);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());

    firebaseCloudService.renewFcmToken(dto);
    return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
  }

  @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "회원가입", notes = "계정의 회원가입을 하는 메서드입니다.")
  public ResponseEntity signUp(@Valid @RequestBody SignUpDto dto) {
    accountService.saveAccount(dto);
    return ResponseEntity.ok(new CommonResponseDto("회원가입이 완료되었습니다."));
  }
}
