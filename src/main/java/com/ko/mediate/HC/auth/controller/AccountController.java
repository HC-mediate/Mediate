package com.ko.mediate.HC.auth.controller;

import com.ko.mediate.HC.auth.annotation.GetAccountInfoSwagger;
import com.ko.mediate.HC.auth.annotation.LoginUser;
import com.ko.mediate.HC.auth.application.dto.response.GetAccountFacadeInfoDto;
import com.ko.mediate.HC.auth.resolver.UserInfo;
import com.ko.mediate.HC.facade.query.AccountQueryFacade;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = "계정 정보 조회")
public class AccountController {
    private final AccountQueryFacade accountQueryFacade;

    @GetAccountInfoSwagger
    @GetMapping(value = "/mypage")
    public ResponseEntity<GetAccountFacadeInfoDto> getAccountInfo(@LoginUser UserInfo userInfo) {
        return ResponseEntity.ok(accountQueryFacade.getAccountInfo(userInfo));
    }
}
