package com.ko.mediate.HC.notification;

import com.ko.mediate.HC.tutoring.domain.TutoringPublishedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class TutoringEventListener {

  private final Logger logger = LoggerFactory.getLogger(TutoringEventListener.class);

  @EventListener
  public void onApplicationEvent(TutoringPublishedEvent event) {
    // todo: firebase 푸시 메시지 발행
    logger.info("Tutoring Event Published!: " + event.getTutoring().getStat().name());
  }
}
