package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateExpiredTokenException extends MediateException {

    public MediateExpiredTokenException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediateExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN);
    }
}
