package com.portfolio.mutex.tims.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@SuppressWarnings("unused")
public final class AppServiceException extends RuntimeException {

  private final String errorField;
  private final int statusCode;

  public AppServiceException(String message) {
    this(message, null, 500);
  }

  public AppServiceException(String message, HttpStatus status) {
    this(message, null, status.value());
  }

  public AppServiceException(String message, int statusCode) {
    this(message, null, statusCode);
  }

  public AppServiceException(String message, String field, HttpStatus status) {
    this(message, field, status.value());
  }

  public AppServiceException(String message, String field, int statusCode) {
    super(message);
    this.errorField = field;
    this.statusCode = statusCode;
  }
}
