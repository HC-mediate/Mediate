package com.ko.mediate.HC.firebase.application;

import com.ko.mediate.HC.auth.application.dto.request.SignInDto;
import com.ko.mediate.HC.firebase.domain.FcmToken;
import com.ko.mediate.HC.firebase.infra.JpaFcmTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FirebaseCloudService {
    private final JpaFcmTokenRepository fcmTokenRepository;

    @Transactional
    public void renewFcmToken(SignInDto dto) {
        Optional<FcmToken> fcmTokenOptional =
                fcmTokenRepository.findFcmTokenByAccountEmail(dto.getEmail());
        FcmToken fcmToken = null;
        if (fcmTokenOptional.isPresent()) {
            fcmToken = fcmTokenOptional.get();
            fcmToken.updateFcmToken(dto.getFcmToken());
            fcmTokenRepository.save(fcmToken);
        } else {
            fcmToken =
                    FcmToken.builder().accountEmail(dto.getEmail()).fcmToken(dto.getFcmToken()).build();
            fcmTokenRepository.save(fcmToken);
        }
    }
}
