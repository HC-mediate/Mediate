package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class MediateUnsupportImageType extends MediateException {

    public MediateUnsupportImageType() {
        super(ErrorCode.UNSUPPORT_IMAGE_TYPE);
    }
}
