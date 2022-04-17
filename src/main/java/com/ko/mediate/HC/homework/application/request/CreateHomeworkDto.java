package com.ko.mediate.HC.homework.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ko.mediate.HC.homework.application.HomeworkContentDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeworkDto {
  private String tutorId;
  private String tuteeId;
  private String title;
  private List<HomeworkContentDto> contents;
}
