package com.ko.mediate.HC.aws.exception;

import com.ko.mediate.HC.common.ErrorCode;
import com.ko.mediate.HC.common.exception.MediateException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageConvertFailureException extends MediateException {

  public ImageConvertFailureException() {
    super(ErrorCode.IMAGE_CONVERT_FAILED);
  }
}
