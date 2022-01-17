package com.ko.mediate.HC.tutoring.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TutoringResponseDto {
  private String accountId;
  private TutoringResponseType type;
}
