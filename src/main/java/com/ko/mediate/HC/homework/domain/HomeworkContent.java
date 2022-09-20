package com.ko.mediate.HC.homework.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class HomeworkContent {
    private String content;
    private Boolean isComplete;

    protected HomeworkContent() {
    }

    public HomeworkContent(String content, Boolean isComplete) {
        this.content = content;
        this.isComplete = isComplete;
    }

    @Override
    public String toString() {
        return "HomeworkContent{" +
                "content='" + content + '\'' +
                ", isComplete=" + isComplete +
                '}';
    }
}
