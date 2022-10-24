package com.ko.mediate.HC.homework.application.dto.response;

import com.ko.mediate.HC.homework.domain.HomeworkContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetHomeworkDto {
    private long homeworkId;
    private String title;
    private boolean isCompleted;
    private List<HomeworkContent> contents;
}
