package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MediateIllegalStateException extends MediateException {
  public MediateIllegalStateException(ErrorCode errorCode) {
    super(ErrorCode.EMAIL_ALREADY_EXIST);
  }
}
