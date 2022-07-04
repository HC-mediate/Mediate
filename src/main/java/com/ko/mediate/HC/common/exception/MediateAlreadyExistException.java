package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class MediateAlreadyExistException extends MediateException {

  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public MediateAlreadyExistException(ErrorCode errorCode) {
    super(errorCode);
  }
}
