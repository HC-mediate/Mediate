package com.ko.mediate.HC.common.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {
    @ApiModelProperty(value = "위도 좌표(X)", required = true)
    private Double latitude;

    @ApiModelProperty(value = "경도 좌표(Y)", required = true)
    private Double longitude;
}
