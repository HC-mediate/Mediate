package com.ko.mediate.HC.homework.application.request;

import com.ko.mediate.HC.homework.application.HomeworkContentDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHomeworkDto {
  private String title;
  private List<HomeworkContentDto> contents;
}
