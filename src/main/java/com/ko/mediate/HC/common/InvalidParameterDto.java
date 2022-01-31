package com.ko.mediate.HC.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class InvalidParameterDto {

  @ApiModelProperty(value = "invalid 파라미터 이름")
  private String parameter;
  @ApiModelProperty(value = "예외 발생 이유")
  private String message;

}