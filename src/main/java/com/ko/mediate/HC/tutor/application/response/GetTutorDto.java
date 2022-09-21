package com.ko.mediate.HC.tutor.application.response;

import com.ko.mediate.HC.tutor.domain.Tutor;
import com.ko.mediate.HC.tutoring.domain.Curriculum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ApiModel(description = "튜터 정보")
@Builder
public class GetTutorDto {
    @ApiModelProperty(value = "튜터 이름")
    private String name;

    @ApiModelProperty(value = "학교 이름")
    private String school;

    @ApiModelProperty(value = "학과 이름")
    private String major;

    @ApiModelProperty(value = "학년")
    private String grade;

    @ApiModelProperty(value = "주소")
    private String address;

    @ApiModelProperty(value = "튜터링할 수 있는 학습 과목")
    private List<Curriculum> curriculums;

    public static GetTutorDto fromEntity(Tutor tutor) {
        return GetTutorDto.builder()
                .name(tutor.getAccount().getName())
                .school(tutor.getAcademicInfo().getSchool())
                .major(tutor.getAcademicInfo().getMajor())
                .grade(tutor.getAcademicInfo().getGrade())
                .address(tutor.getAddress())
                .curriculums(tutor.getCurriculums())
                .build();
    }
}
