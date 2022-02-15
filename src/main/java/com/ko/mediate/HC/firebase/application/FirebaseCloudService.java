package com.ko.mediate.HC.firebase.application;

import com.ko.mediate.HC.firebase.domain.FcmToken;
import com.ko.mediate.HC.firebase.infra.JpaFcmTokenRepository;
import com.ko.mediate.HC.tutoring.application.dto.request.LoginDto;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FirebaseCloudService {
  private final JpaFcmTokenRepository fcmTokenRepository;

  @Transactional
  public void renewFcmToken(LoginDto dto) {
    Optional<FcmToken> fcmTokenOptional = fcmTokenRepository.findFcmTokenByAccountId(dto.getId());
    FcmToken fcmToken = null;
    if (fcmTokenOptional.isPresent()) {
      fcmToken = fcmTokenOptional.get();
      fcmToken.updateFcmToken(dto.getFcmToken());
      fcmTokenRepository.save(fcmToken);
    } else {
      fcmToken = FcmToken.builder().accountId(dto.getId()).fcmToken(dto.getFcmToken()).build();
      fcmTokenRepository.save(fcmToken);
    }
  }
}
