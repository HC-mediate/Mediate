package com.ko.mediate.HC.homework.application.dto.request;

import com.ko.mediate.HC.homework.application.HomeworkContentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateHomeworkDto {
    private String title;
    private List<HomeworkContentDto> contents;
}
