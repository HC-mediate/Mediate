package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateNotFoundException extends MediateException {

  @Override
  public HttpStatus status() {
    return HttpStatus.NOT_FOUND;
  }

  public MediateNotFoundException() {}

  public MediateNotFoundException(String message) {
    super(message);
  }
}
