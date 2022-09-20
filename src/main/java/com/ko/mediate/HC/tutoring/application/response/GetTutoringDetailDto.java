package com.ko.mediate.HC.tutoring.application.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetTutoringDetailDto {
    @ApiModelProperty(value = "튜터링 이름")
    private String tutoringName;

    @ApiModelProperty(value = "시작 일자")
    private String startedAt;

    @ApiModelProperty(value = "진행 주차")
    private List<GetProgressDto> progresses;
}
