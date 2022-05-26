package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;

@Getter
public enum TutoringStat {
  WAITING_ACCEPT("튜터링 응답 대기 중"),
  LEARNING("튜터링 진행 중"),
  COMPLETE_TUTORING("튜터링 완료"),
  CANCEL("튜터링 거절");
  public final String description;

  TutoringStat(String description) {
    this.description = description;
  }
}
