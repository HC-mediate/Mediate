package com.ko.mediate.HC.auth.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileImage {
    @Column(name = "profile_key")
    private String profileKey;

    @Column(name = "profile_url")
    private String profileUrl;
}
