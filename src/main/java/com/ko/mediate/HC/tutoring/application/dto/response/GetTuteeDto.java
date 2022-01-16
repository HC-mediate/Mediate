package com.ko.mediate.HC.tutoring.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetTuteeDto {
  private String name;
  private String school;
  private String grade;
  private String address;
}
