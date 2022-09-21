package com.ko.mediate.HC.homework.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkContentDto {
    private String content;
    private boolean isCompleted;
}
