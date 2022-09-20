package com.ko.mediate.HC.common;

import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class ErrorResponseBuilder {

    private ErrorResponseBuilder() {
    }

    public static ErrorResponseDto build(
            final String code, final String message, final HttpStatus status) {
        return buildDetails(code, message, status);
    }

    public static ErrorResponseDto build(
            final String code,
            final String message,
            final HttpStatus status,
            final List<InvalidParameterDto> invalidParameters) {
        return buildDetails(code, message, status, invalidParameters);
    }

    private static ErrorResponseDto buildDetails(
            final String code, final String message, final HttpStatus status) {
        ErrorResponseDto errorResponseDto =
                new ErrorResponseDto(code, message, status.value(), LocalDateTime.now(), new ArrayList<>());
        return errorResponseDto;
    }

    private static ErrorResponseDto buildDetails(
            final String code,
            final String message,
            final HttpStatus status,
            final List<InvalidParameterDto> invalidParameters) {
        ErrorResponseDto errorResponseDetails =
                new ErrorResponseDto(code, message, status.value(), LocalDateTime.now(), new ArrayList<>());

        if (!CollectionUtils.isEmpty(invalidParameters)) {
            errorResponseDetails =
                    new ErrorResponseDto(
                            code, message, status.value(), LocalDateTime.now(), invalidParameters);
        }
        return errorResponseDetails;
    }
}
