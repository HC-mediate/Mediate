package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateNotFoundException extends MediateException {

    public MediateNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediateNotFoundException() {
        super(ErrorCode.ENTITY_NOT_FOUND);
    }
}
