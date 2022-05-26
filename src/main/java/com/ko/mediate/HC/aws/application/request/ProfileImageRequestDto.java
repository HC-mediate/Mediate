package com.ko.mediate.HC.aws.application.request;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProfileImageRequestDto {
  @ApiModelProperty(value = "업로드할 이미지")
  @NotNull(message = "이미지 파일은 반드시 있어야 합니다.")
  private MultipartFile file;
}
