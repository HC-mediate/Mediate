package com.ko.mediate.HC.tutoring.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAccountDto {
  private String accountId;
  private String phoneNum;
}
