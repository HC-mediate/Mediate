package com.ko.mediate.HC.oauth2.infra.google;

import com.ko.mediate.HC.oauth2.infra.google.dto.response.GoogleProfileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "GoogleOAuth2Client", url = "${oauth2.client.google.base-url}")
public interface GoogleOAuth2Client {

    @GetMapping("${oauth2.client.google.path}")
    GoogleProfileDto getProfile(@RequestHeader(HttpHeaders.AUTHORIZATION) String accessToken);
}
