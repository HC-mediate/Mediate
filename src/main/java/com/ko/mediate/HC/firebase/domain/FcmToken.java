package com.ko.mediate.HC.firebase.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "tb_fcm_token")
@Getter
public class FcmToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_email", unique = true)
    private String accountEmail;

    @Column(name = "fcm_token")
    private String fcmToken;

    protected FcmToken() {
    }

    ;

    @Builder
    public FcmToken(String accountEmail, String fcmToken) {
        this.accountEmail = accountEmail;
        this.fcmToken = fcmToken;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
