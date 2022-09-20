package com.ko.mediate.HC.aws.application.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImageResponseDto {

    @ApiModelProperty(value = "S3 파일 업로드 경로")
    private String key;

    @ApiModelProperty(value = "이미지 접근 경로")
    private String url;
}
