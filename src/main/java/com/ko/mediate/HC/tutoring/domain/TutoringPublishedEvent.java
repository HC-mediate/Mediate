package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TutoringPublishedEvent extends ApplicationEvent {
  private final Tutoring tutoring;

  public TutoringPublishedEvent(Object source) {
    super(source);
    this.tutoring = (Tutoring) source;
  }
}
