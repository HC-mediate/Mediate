package com.ko.mediate.HC.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponseDto {

  @ApiModelProperty(value = "발생 코드")
  private String code;
  @ApiModelProperty(value = "발생 이유")
  private String message;
  @ApiModelProperty(value = "Http 상태 코드")
  private Integer status;
  @ApiModelProperty(value = "발생 시간")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime timestamp;
  @ApiModelProperty(value = "원인 파라미터 (비어있을 수 있음)")
  private List<InvalidParameterDto> invalidParameters = new ArrayList<>();

}
