package com.ko.mediate.HC.tutoring.application.request;

import lombok.Getter;

@Getter
public enum TutoringResponseType {
    ACCEPT("요청을 수락했습니다."),
    REFUSE("요청을 거절했습니다.");
    private final String message;

    TutoringResponseType(String message) {
        this.message = message;
    }
}
