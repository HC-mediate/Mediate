package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public class MediateAlreadyExistException extends MediateException{

  @Override
  public HttpStatus status() {
    return HttpStatus.BAD_REQUEST;
  }

  public MediateAlreadyExistException() {
  }

  public MediateAlreadyExistException(String message) {
    super(message);
  }
}
