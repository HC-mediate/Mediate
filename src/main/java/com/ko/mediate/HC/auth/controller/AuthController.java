package com.ko.mediate.HC.auth.controller;

import com.amazonaws.util.StringUtils;
import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.annotation.LogoutSwgger;
import com.ko.mediate.HC.auth.annotation.ReissueTokenSwagger;
import com.ko.mediate.HC.auth.annotation.SignInSwagger;
import com.ko.mediate.HC.auth.annotation.SignUpSwagger;
import com.ko.mediate.HC.auth.application.AccountService;
import com.ko.mediate.HC.auth.application.AuthService;
import com.ko.mediate.HC.auth.application.request.SignInDto;
import com.ko.mediate.HC.auth.application.request.SignUpDto;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import com.ko.mediate.HC.firebase.application.FirebaseCloudService;
import com.ko.mediate.HC.jwt.JwtFilter;
import com.ko.mediate.HC.auth.application.response.TokenDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
@Api(tags = {"로그인, 로그아웃, 회원가입, 액세스 토큰 재발급"})
public class AuthController {
  private final FirebaseCloudService firebaseCloudService;
  private final AccountService accountService;
  private final AuthService authService;

  @PostMapping("/signin")
  @SignInSwagger
  public ResponseEntity<TokenDto> signIn(@Valid @RequestBody SignInDto dto) {
    TokenDto tokenDto = authService.signIn(dto);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());
    firebaseCloudService.renewFcmToken(dto);
    return new ResponseEntity<>(tokenDto, httpHeaders, HttpStatus.OK);
  }

  //todo: 이메일 중복 시 http 상태코드랑 body에 이유를 같이 포함시켜서 전달
  @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
  @SignUpSwagger
  public ResponseEntity signUp(@Valid @RequestBody SignUpDto dto) {
    accountService.saveAccount(dto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @DeleteMapping(value = "/logout")
  @LogoutSwgger
  public ResponseEntity logout(@ApiIgnore @LoginUser UserInfo userInfo) {
    authService.logout(userInfo);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/refresh")
  @ReissueTokenSwagger
  public ResponseEntity<TokenDto> reissueToken(@RequestHeader(value = "Refresh") String auth) {
    if (StringUtils.isNullOrEmpty(auth)) {
      throw new MediateIllegalStateException(ErrorCode.NO_REFRESH_TOKEN);
    }
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(authService.reissueAccessTokenByRefreshToken(auth.substring(7)));
  }
}
