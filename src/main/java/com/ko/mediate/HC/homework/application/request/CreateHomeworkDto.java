package com.ko.mediate.HC.homework.application.request;

import com.ko.mediate.HC.homework.application.HomeworkContentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHomeworkDto {
    private String tutorId;
    private String tuteeId;
    private String title;
    private List<HomeworkContentDto> contents;
}
