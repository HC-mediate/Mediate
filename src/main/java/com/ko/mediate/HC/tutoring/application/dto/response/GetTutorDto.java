package com.ko.mediate.HC.tutoring.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTutorDto {
  private String name;
  private String school;
  private String major;
  private String grade;
  private String address;
}
