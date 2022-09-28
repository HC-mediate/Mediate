package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateIllegalStateException extends MediateException {
    public MediateIllegalStateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
