package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateDeactivatedException extends MediateException {

    public MediateDeactivatedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediateDeactivatedException() {
        super(ErrorCode.DEACTIVATED_ACCOUNT);
    }
}
