package com.ko.mediate.HC.tutoring.application.dto.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TutoringResponseDto {
    @ApiModelProperty(value = "응답 유형 (수락/거절)")
    private TutoringResponseType responseType;

    @ApiModelProperty(value = "튜터/튜티 타입")
    private RoleType role;
}
