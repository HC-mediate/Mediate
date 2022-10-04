package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class MediateNotFoundTokenException extends AuthenticationException {

    private ErrorCode errorCode;

    public MediateNotFoundTokenException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public MediateNotFoundTokenException() {
        super(ErrorCode.NO_ACCESS_TOKEN.getDescription());
        this.errorCode = ErrorCode.NO_ACCESS_TOKEN;
    }
}
