package com.ko.mediate.HC.tutoring.application.dto.request;

import com.ko.mediate.HC.tutoring.application.RoleType;
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
  @NotEmpty(message = "ID는 반드시 있어야 합니다.")
  private String id;

  @ApiModelProperty(name = "계정 비밀번호", required = true)
  @NotEmpty(message = "비밀번호는 반드시 있어야 합니다.")
  private String password;

  @ApiModelProperty(name = "사용자 이름", required = true)
  @NotEmpty(message = "사용자 이름은 필수입니다.")
  private String name;

  @ApiModelProperty(name = "사용자 전화번호", required = true)
  @NotEmpty(message = "전화번호는 필수입니다.")
  private String phoneNum;

  @ApiModelProperty(name = "학교 이름", required = true)
  @NotEmpty(message = "학교 이름을 입력해주세요")
  private String school;

  @ApiModelProperty(name = "학과 이름", required = true)
  @NotEmpty(message = "학과를 입력해주세요")
  private String major;

  @ApiModelProperty(name = "학년", required = true)
  @NotEmpty(message = "학년을 입력해주세요")
  private String grade;

  @ApiModelProperty(name = "튜터링 가능 지역", required = true)
  @NotEmpty(message = "주소를 입력해주세요")
  private String address;

  @ApiModelProperty(name = "튜터/튜티 타입", required = true)
  @NotEmpty(message = "타입은 반드시 있어야 합니다.")
  private RoleType type;

  @ApiModelProperty(name = "교육가능한 교과 과정", required = true)
  @NotEmpty(message = "교육가능한 교과 과정을 입력해주세요")
  private Curriculum curriculum;
}
