package com.portfolio.mutex.tims.exception;

import com.portfolio.mutex.tims.dto.ExceptionDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class AppExceptionHandler {

  /**
   * Handles the {@link AppServiceException} if it occurs
   *
   * @param exception the exception
   * @return custom response based on the exception
   */
  @ExceptionHandler(AppServiceException.class)
  public ResponseEntity<ExceptionDto> handleAppServiceException(AppServiceException exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(exception.getStatusCode())
        .body(toExceptionDto(exception.getMessage(), exception.getErrorField()));
  }

  /**
   * Handles generic {@link Exception} if it occurs
   *
   * @param exception the exception
   * @return custom response based on the exception
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ExceptionDto> handleException(Exception exception) {
    log.error(exception.getMessage(), exception);
    if (exception instanceof BadCredentialsException) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
          .body(toExceptionDto("The email or password is incorrect."));
    } else if (exception instanceof AccountStatusException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(toExceptionDto("This account is locked"));
    } else if (exception instanceof AccessDeniedException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(toExceptionDto("You are not authorized to access this resource"));
    } else if (exception instanceof SignatureException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(toExceptionDto("The JWT signature is invalid"));
    } else if (exception instanceof ExpiredJwtException) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN)
          .body(toExceptionDto("The JWT token has expired."));
    } else {
      return ResponseEntity.internalServerError().body(toExceptionDto(exception.getMessage()));
    }
  }

  /**
   * Creates an exception DTO based on the provided message
   *
   * @param message the error message
   * @return exception details
   */
  private ExceptionDto toExceptionDto(String message) {
    return toExceptionDto(message, null);
  }

  /**
   * Creates an exception DTO based on the provided message and field
   *
   * @param message the error message
   * @param field   the specific field where the error occurs
   * @return exception details
   */
  private ExceptionDto toExceptionDto(String message, String field) {
    return ExceptionDto.builder()
        .message(message)
        .field(field)
        .build();
  }
}
