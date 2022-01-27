package com.ko.mediate.HC.tutoring.application.dto.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TutoringResponseDto {
  @ApiModelProperty(value = "요청에 대한 응답을 보낼 계정 ID")
  @NotEmpty(message = "응답을 보낼 계정 ID는 반드시 있어야 합니다.")
  private String toAccountId;

  @ApiModelProperty(value = "튜터/튜티 타입")
  @NotEmpty(message = "타입은 반드시 있어야 합니다.")
  private RoleType type;

  @ApiModelProperty(value = "응답 유형 (수락/거절)")
  @NotEmpty(message = "응답은 반드시 있어야 합니다.")
  private TutoringResponseType responseType;
}
