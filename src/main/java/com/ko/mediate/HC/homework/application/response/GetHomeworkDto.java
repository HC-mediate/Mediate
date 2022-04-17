package com.ko.mediate.HC.homework.application.response;

import com.ko.mediate.HC.homework.domain.HomeworkContent;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeworkDto {
  private long homeworkId;
  private String title;
  private boolean isCompleted;
  private List<HomeworkContent> contents;
}
