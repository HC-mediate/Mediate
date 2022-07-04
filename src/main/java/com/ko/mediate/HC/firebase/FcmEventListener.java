package com.ko.mediate.HC.firebase;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateNotFoundException;
import com.ko.mediate.HC.firebase.infra.FirebaseCloudMessenger;
import com.ko.mediate.HC.firebase.infra.JpaFcmTokenRepository;
import com.ko.mediate.HC.tutoring.domain.TutoringPublishedEvent;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FcmEventListener {

  private final Logger logger = LoggerFactory.getLogger(FcmEventListener.class);

  private final FirebaseCloudMessenger firebaseCloudMessenger;
  private final JpaFcmTokenRepository fcmTokenRepository;

  @EventListener
  public void onApplicationEvent(TutoringPublishedEvent event) throws IOException {
    String sendToAccountEmail = event.getSendToAccountEmail();
    if (sendToAccountEmail == null || sendToAccountEmail.isEmpty()) {
      logger.info("[FcmEventListener] sendToAccountId is Null or Empty");
      return;
    }
    String sendToToken =
        fcmTokenRepository
            .findFcmTokenByAccountEmail(sendToAccountEmail)
            .orElseThrow(() -> new MediateNotFoundException(ErrorCode.ENTITY_NOT_FOUND))
            .getFcmToken();
    firebaseCloudMessenger.sendMessageTo(sendToToken, event.getTitle(), event.getBody());
    logger.info(
        "[FcmEventListener] Tutoring Event Published!: " + event.getTutoring().getStat().name());
  }
}
