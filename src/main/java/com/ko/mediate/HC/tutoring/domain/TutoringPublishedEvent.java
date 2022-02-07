package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TutoringEvent extends ApplicationEvent {
  private final Tutoring tutoring;

  public TutoringEvent(Object source) {
    super(source);
    this.tutoring = (Tutoring) source;
  }
}
