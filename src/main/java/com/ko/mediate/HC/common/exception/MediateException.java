package com.ko.mediate.HC.common.exception;

import org.springframework.http.HttpStatus;

public abstract class MediateException extends RuntimeException {
  public MediateException() {}

  public MediateException(final String message) {
    super(message);
  }

  public abstract HttpStatus status();
}
