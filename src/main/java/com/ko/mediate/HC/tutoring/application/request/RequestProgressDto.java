package com.ko.mediate.HC.tutoring.application.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RequestProgressDto {
  @Min(value = 1, message = "주차는 적어도 1주차 이상이어야 합니다.")
  private int week;

  @NotEmpty(message = "진행도 내용은 반드시 있어야 합니다.")
  private String content;

  private Boolean isCompleted;
}
