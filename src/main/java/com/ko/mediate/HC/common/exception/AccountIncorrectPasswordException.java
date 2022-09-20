package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;

public class AccountIncorrectPasswordException extends MediateException {
    public AccountIncorrectPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
