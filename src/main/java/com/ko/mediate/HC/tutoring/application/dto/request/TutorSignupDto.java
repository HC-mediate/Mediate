package com.ko.mediate.HC.tutoring.application.dto.request;

import com.ko.mediate.HC.tutoring.domain.Curriculum;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TutorSignupDto {
  @ApiModelProperty(name = "계정 ID", required = true)
  @NotEmpty
  private String id;

  @ApiModelProperty(name = "계정 비밀번호", required = true)
  @NotEmpty
  private String password;

  @ApiModelProperty(name = "사용자 이름", required = true)
  @NotEmpty
  private String name;

  @ApiModelProperty(name = "학교 이름", required = true)
  @NotEmpty
  private String school;

  @ApiModelProperty(name = "학과 이름", required = true)
  @NotEmpty
  private String major;

  @ApiModelProperty(name = "학년", required = true)
  @NotEmpty
  private String grade;

  @ApiModelProperty(name = "튜터링 가능 지역", required = true)
  @NotEmpty
  private String address;

  @ApiModelProperty(name = "튜터/튜티 타입", required = true)
  private SignupType type;

  @ApiModelProperty(name = "교육가능한 교과 과정", required = true)
  private Curriculum curriculum;
}
