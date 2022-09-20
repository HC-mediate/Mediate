package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateFileExceedLimitException extends MediateException {
    public MediateFileExceedLimitException(ErrorCode errorCode) {
        super(errorCode);
    }
}
