package com.ko.mediate.HC.community.domain;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateIllegalStateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttachedImage {
    @Column(name = "image_key", columnDefinition = "TEXT")
    private String imageKey;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    public static AttachedImage createAttachedImage(String imageKey, String imageUrl){
        if(imageKey == null || imageKey.length() == 0){
            throw new MediateIllegalStateException(ErrorCode.NULL_PROPERTY);
        }
        if(imageUrl == null || imageUrl.length() == 0){
            throw new MediateIllegalStateException(ErrorCode.NULL_PROPERTY);
        }
        return new AttachedImage(imageKey, imageUrl);
    }
}
