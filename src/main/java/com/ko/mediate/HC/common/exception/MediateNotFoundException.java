package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;

public class MediateNotFoundException extends MediateException {

  @Override
  public HttpStatus status() {
    return HttpStatus.NOT_FOUND;
  }

  public MediateNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
