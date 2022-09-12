package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MediateAlreadyExistException extends MediateException {
  public MediateAlreadyExistException(ErrorCode errorCode) {
    super(errorCode);
  }
}
