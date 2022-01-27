package com.ko.mediate.HC.tutoring.application.dto.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RequestTutoringDto {
  @ApiModelProperty(value = "튜터링 이름")
  @NotEmpty(message = "튜터링 이름은 반드시 있어야 합니다.")
  private String tutoringName;

  @ApiModelProperty(value = "튜터 ID")
  @NotEmpty(message = "튜터의 ID는 반드시 있어야 합니다.")
  private String tutorId;

  @ApiModelProperty(value = "튜티 ID")
  @NotEmpty(message = "튜티의 ID는 반드시 있어야 합니다.")
  private String tuteeId;
}
