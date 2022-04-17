package com.ko.mediate.HC.tutoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProgressDto {
  private int week;
  private String content;
  private Boolean isCompleted;
}
