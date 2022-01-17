package com.ko.mediate.HC.tutoring.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(description = "튜터 정보")
public class GetTutorDto {
  @ApiModelProperty(value = "튜터 이름")
  private String name;
  @ApiModelProperty(value = "학교 이름")
  private String school;
  @ApiModelProperty(value = "학과 이름")
  private String major;
  @ApiModelProperty(value = "학년")
  private String grade;
  @ApiModelProperty(value = "주소")
  private String address;
}
