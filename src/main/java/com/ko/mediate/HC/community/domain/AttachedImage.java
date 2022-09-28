package com.ko.mediate.HC.community.domain;

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
public class AttachedImage {
    @Column(name = "image_key", columnDefinition = "TEXT")
    private String imageKey;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;
}
