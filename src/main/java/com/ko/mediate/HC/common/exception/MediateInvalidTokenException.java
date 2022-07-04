package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;

public class MediateInvalidTokenException extends MediateException {

  public MediateInvalidTokenException() {
    super(ErrorCode.INVALID_TOKEN);
  }

  public MediateInvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }

  @Override
  public HttpStatus status() {
    return HttpStatus.FORBIDDEN;
  }
}
