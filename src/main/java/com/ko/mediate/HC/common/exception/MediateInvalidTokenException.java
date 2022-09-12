package com.ko.mediate.HC.common.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MediateInvalidTokenException extends MediateException {
  public MediateInvalidTokenException() {
    super(ErrorCode.INVALID_TOKEN);
  }
}
