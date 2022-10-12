package com.ko.mediate.HC.oauth2.infra.naver;

import com.ko.mediate.HC.oauth2.infra.naver.dto.response.NaverProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "NaverOAuth2Client", url = "${oauth2.client.naver.base-url}")
public interface NaverOAuth2Client {

    @GetMapping("${oauth2.client.naver.path}")
    NaverProfileDto getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
