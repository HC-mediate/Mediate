package com.ko.mediate.HC.tutoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestTutoringDto {
  private String fromId;
  private String toId;
}
