package com.s2u2m.examples.interviews.error;

import java.text.MessageFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class PlatformException extends RuntimeException {
  private final HttpStatus status;
  private final ErrorCode code;

  public PlatformException(HttpStatus status, ErrorCode code, String message) {
    super(message);
    this.status = status;
    this.code = code;
  }

  public PlatformException(HttpStatus status, ErrorCode code, String pattern, Object... args) {
    this(status, code, MessageFormat.format(pattern, args));
  }
}
