package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateIllegalStateException extends MediateException{
  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public MediateIllegalStateException() {
  }

  public MediateIllegalStateException(String message) {
    super(message);
  }
}
