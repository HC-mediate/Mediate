package com.ko.mediate.HC.firebase.application;

import com.ko.mediate.HC.auth.application.dto.request.OAuth2SignInDto;
import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.firebase.domain.FcmToken;
import com.ko.mediate.HC.firebase.infra.JpaFcmTokenRepository;
import com.ko.mediate.HC.oauth2.application.OAuth2Service;
import com.ko.mediate.HC.oauth2.domain.Profile;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FirebaseCloudService {

    private final JpaFcmTokenRepository fcmTokenRepository;

    private final OAuth2Service oAuth2Service;

    @Transactional
    public void renewFcmToken(SignInDto dto) {
        FcmToken fcmToken = getFcmToken(dto.getEmail(), dto.getFcmToken());
        fcmTokenRepository.save(fcmToken);
    }

    @Transactional
    public void renewFcmToken(OAuth2SignInDto dto) {
        Profile oauth2Profile = oAuth2Service.getProfile(dto.getSocialType(), dto.getAccessToken());
        FcmToken fcmToken = getFcmToken(oauth2Profile.getEmail(), dto.getFcmToken());
        fcmTokenRepository.save(fcmToken);
    }

    private FcmToken getFcmToken(String email, String newFcmToken) {
        Optional<FcmToken> fcmTokenOptional = fcmTokenRepository.findFcmTokenByAccountEmail(email);
        if (fcmTokenOptional.isPresent()) {
            FcmToken fcmToken = fcmTokenOptional.get();
            fcmToken.updateFcmToken(newFcmToken);
            return fcmToken;
        }
        return FcmToken.builder()
                .accountEmail(email)
                .fcmToken(newFcmToken)
                .build();
    }
}
