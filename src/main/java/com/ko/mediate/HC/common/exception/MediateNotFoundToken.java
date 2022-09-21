package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class MediateNotFoundToken extends MediateException {
    public MediateNotFoundToken(ErrorCode errorCode) {
        super(errorCode);
    }
}
