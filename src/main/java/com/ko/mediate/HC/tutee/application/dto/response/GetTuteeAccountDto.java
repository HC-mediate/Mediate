package com.ko.mediate.HC.tutee.application.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(description = "튜티 마이페이지 정보")
public class GetTuteeAccountDto {
    @ApiModelProperty(value = "계정 ID")
    private String accountId;

    @ApiModelProperty(value = "전화번호")
    private String phoneNum;

    @ApiModelProperty(value = "튜티 이름")
    private String name;

    @ApiModelProperty(value = "학교 이름")
    private String school;

    @ApiModelProperty(value = "학년")
    private String grade;

    @ApiModelProperty(value = "주소")
    private String address;
}
