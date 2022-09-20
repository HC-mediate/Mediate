package com.ko.mediate.HC.community.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Locale;

public enum ArticleType {
    ANONYMOUS("Anonymous", "익명"),
    QUESTION("Question", "질문");
    private String EnumEngString;
    private String EnumKorString;

    ArticleType(String enumEngString, String enumKorString) {
        EnumEngString = enumEngString;
        EnumKorString = enumKorString;
    }

    @JsonCreator
    public static ArticleType from(String s) {
        return ArticleType.valueOf(s.toUpperCase(Locale.ROOT));
    }
}
