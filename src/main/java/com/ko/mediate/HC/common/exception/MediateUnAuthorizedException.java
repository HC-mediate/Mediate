package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateUnAuthorizedException extends MediateException {
    public MediateUnAuthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
