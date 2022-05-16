package com.ko.mediate.HC.auth.application.response;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class GetAccountDto {
  @ApiModelProperty(value = "계정 ID")
  private String accountId;

  private String name;
  private String phoneNum;
  private RoleType type;
}
