package com.ko.mediate.HC.oauth2.application;

import com.ko.mediate.HC.oauth2.domain.Profile;
import com.ko.mediate.HC.oauth2.domain.SocialType;
import com.ko.mediate.HC.oauth2.infra.google.GoogleOAuth2Client;
import com.ko.mediate.HC.oauth2.infra.kakao.KakaoOAuth2Client;
import com.ko.mediate.HC.oauth2.infra.kakao.dto.response.KakaoProfile;
import com.ko.mediate.HC.oauth2.infra.kakao.dto.response.KakaoProfileDto;
import com.ko.mediate.HC.oauth2.infra.naver.NaverOAuth2Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OAuth2Service {

    private final GoogleOAuth2Client googleOAuth2Client;

    private final KakaoOAuth2Client kakaoOAuth2Client;

    private final NaverOAuth2Client naverOAuth2Client;

    public Profile getProfile(SocialType socialType, String accessToken) {
        switch (socialType) {
            case GOOGLE:
                return googleOAuth2Client.getProfile(accessToken).toProfile();
            case KAKAO:
                return kakaoOAuth2Client.getProfile(accessToken).toProfile();
            case NAVER:
                return naverOAuth2Client.getProfile(accessToken).toProfile();
            default:
                throw new IllegalArgumentException();
        }
    }
}
