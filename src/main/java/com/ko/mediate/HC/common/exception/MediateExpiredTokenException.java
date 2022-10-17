package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class MediateExpiredTokenException extends AuthenticationException {

    private ErrorCode errorCode;

    public MediateExpiredTokenException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }

    public MediateExpiredTokenException() {
        super(ErrorCode.EXPIRED_TOKEN.getDescription());
        errorCode = ErrorCode.EXPIRED_TOKEN;
    }
}
