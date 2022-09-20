package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateAlreadyExistException extends MediateException {
    public MediateAlreadyExistException(ErrorCode errorCode) {
        super(errorCode);
    }
}
