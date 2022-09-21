package com.ko.mediate.HC.tutoring.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

//todo: 튜터링 이벤트에 설정할 메시지
@Getter
public class TutoringPublishedEvent extends ApplicationEvent {
    private final Tutoring tutoring;
    private String sendToAccountEmail;
    private String fromToAccountEmail;
    private String title;
    private String body;

    public TutoringPublishedEvent(Object source) {
        super(source);
        this.tutoring = (Tutoring) source;

    }
}
