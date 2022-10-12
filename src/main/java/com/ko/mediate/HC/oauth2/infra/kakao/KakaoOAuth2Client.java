package com.ko.mediate.HC.oauth2.infra.kakao;

import com.ko.mediate.HC.oauth2.infra.kakao.dto.response.KakaoProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "KakaoOAuth2Client", url = "${oauth2.client.kakao.base-url}")
public interface KakaoOAuth2Client {

    @GetMapping("${oauth2.client.kakao.path}")
    KakaoProfileDto getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
