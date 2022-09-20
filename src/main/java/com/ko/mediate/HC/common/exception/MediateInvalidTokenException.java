package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateInvalidTokenException extends MediateException {
    public MediateInvalidTokenException() {
        super(ErrorCode.INVALID_TOKEN);
    }
}
