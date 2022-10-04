package com.ko.mediate.HC.community.domain;

public enum Category {
    STUDY_QUESTION("공부질문"),
    UNIVERSITY_ADMISSION("대학입시"),
    TROUBLE_COUNSEL("고민상담");
    private String name;

    Category(String name) {
        this.name = name;
    }
}
