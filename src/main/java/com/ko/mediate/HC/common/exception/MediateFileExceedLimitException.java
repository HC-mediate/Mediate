package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateFileExceedLimitException extends MediateException {
  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public MediateFileExceedLimitException() {}

  public MediateFileExceedLimitException(String message) {
    super(message);
  }
}
