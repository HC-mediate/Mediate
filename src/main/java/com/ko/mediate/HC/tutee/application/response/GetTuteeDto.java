package com.ko.mediate.HC.tutee.application.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value = "튜티 정보")
public class GetTuteeDto {
  @ApiModelProperty(value = "튜티 이름")
  private String name;

  @ApiModelProperty(value = "학교 이름")
  private String school;

  @ApiModelProperty(value = "학년")
  private String grade;

  @ApiModelProperty(value = "주소")
  private String address;
}
