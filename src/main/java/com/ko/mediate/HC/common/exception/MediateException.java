package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import lombok.Getter;

@Getter
public abstract class MediateException extends RuntimeException {
    private final ErrorCode errorCode;

    public MediateException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
