package com.ko.mediate.HC.tutor.application.request;

import com.ko.mediate.HC.common.domain.Location;
import com.ko.mediate.HC.common.domain.LocationValid;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutorSignupDto {
  @ApiModelProperty(value = "학교 이름", required = true)
  @NotBlank(message = "학교 이름을 입력해주세요")
  private String school;

  @ApiModelProperty(value = "학과 이름", required = true)
  @NotBlank(message = "학과를 입력해주세요")
  private String major;

  @ApiModelProperty(value = "학년", required = true)
  @NotBlank(message = "학년을 입력해주세요")
  private String grade;

  @ApiModelProperty(value = "튜터링 가능 지역", required = true)
  @NotBlank(message = "주소를 입력해주세요")
  private String address;

  @ApiModelProperty(value = "교육가능한 교과 과정", required = true)
  @NotNull(message = "교과 과정은 반드시 입력해야 합니다.")
  private List<Curriculum> curriculums;

  @ApiModelProperty(value = "튜터의 교육가능 지역 GPS", required = true)
  @NotNull(message = "튜터의 GPS 값을 입력해주세요")
  @LocationValid
  private Location location;
}
