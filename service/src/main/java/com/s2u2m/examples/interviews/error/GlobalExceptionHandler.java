package com.s2u2m.examples.interviews.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(PlatformException.class)
  public ResponseEntity<ErrorResponseBody> handleException(PlatformException ex) {
    log.error(ex.getMessage(), ex);
    ErrorCode code = ex.getCode();
    return ResponseEntity.status(ex.getStatus())
        .body(code.getErrorResponse());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponseBody> handleException(
      MethodArgumentNotValidException ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.badRequest()
        .body(ErrorCode.INVALID_REQUEST_PARAMS.getErrorResponse());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponseBody> handleException(Exception ex) {
    log.error(ex.getMessage(), ex);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }
}
