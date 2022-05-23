package com.ko.mediate.HC.auth.controller;

import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.application.response.GetAccountInfoDto;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.CommonResponseDto;
import com.ko.mediate.HC.firebase.application.FirebaseCloudService;
import com.ko.mediate.HC.jwt.JwtFilter;
import com.ko.mediate.HC.jwt.TokenProvider;
import com.ko.mediate.HC.tutoring.application.dto.response.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"로그인용 api"})
public class AuthController {
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final FirebaseCloudService firebaseCloudService;
  private final AccountService accountService;

  @PostMapping("/sign-in")
  @ApiOperation(
      value = "로그인",
      notes = "성공시 jwt 토큰을 헤더와 응답 값에 넣어 반환합니다.",
      produces = "application/json",
      response = TokenDto.class)
  public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto dto) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(dto.getAccountId(), dto.getPassword());
    Authentication authentication =
        authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.createToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

    firebaseCloudService.renewFcmToken(dto);
    return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
  }

  @PostMapping(value = "/sign-up", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation(value = "회원가입", notes = "계정의 회원가입을 하는 메서드입니다.")
  public ResponseEntity signUp(@Valid @RequestBody SignUpDto dto) {
    accountService.saveAccount(
        dto.getAccountId(), dto.getPassword(), dto.getNickname(), dto.getPhoneNum());
    return ResponseEntity.ok(new CommonResponseDto("회원가입이 완료되었습니다."));
  }

  @ApiOperation(value = "마이페이지", notes = "현재 로그인된 튜터/튜티마다 마이페이지를 다르게 보여줍니다.")
  @GetMapping(value = "/mypage")
  public ResponseEntity<GetAccountInfoDto> getAccountInfo(@LoginUser UserInfo userInfo) {
    return ResponseEntity.ok(accountService.getAccountInfo(userInfo));
  }
}
