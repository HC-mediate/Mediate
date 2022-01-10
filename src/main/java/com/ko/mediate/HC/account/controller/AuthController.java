package com.ko.mediate.HC.account.controller;

import com.ko.mediate.HC.account.application.AccountJoinService;
import com.ko.mediate.HC.account.application.dto.TokenDto;
import com.ko.mediate.HC.account.application.dto.request.LoginDto;
import com.ko.mediate.HC.account.application.dto.request.SignupDto;
import com.ko.mediate.HC.jwt.JwtFilter;
import com.ko.mediate.HC.jwt.TokenProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class AuthController {
  private final AccountJoinService joinService;
  private final TokenProvider tokenProvider;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;

  @PostMapping(value = "/signup")
  public ResponseEntity<String> Signup(@Valid @RequestBody SignupDto dto) {
    joinService.Join(dto);
    return ResponseEntity.ok("회원가입이 완료되었습니다.");
  }

  @PostMapping("/auth")
  public ResponseEntity<TokenDto> Authorize(@Valid @RequestBody LoginDto loginDto) {
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getId(), loginDto.getPassword());
    Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.createToken(authentication);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
    return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
  }
}
