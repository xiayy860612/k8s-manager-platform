package com.s2u2m.examples.interviews.error;

import lombok.ToString;

@ToString
public enum ErrorCode {
  INVALID_REQUEST_PARAMS(51, "invalid parameters"),
  PROVIDER_NOT_SUPPORTED(101, "not supported k8s provider"),
  CLUSTER_ALREADY_EXISTED(102, "cluster already existed"),
  PROVIDER_SERVICE_NOT_AVAILABLE(103, "k8s provider service is not available")
  ;

  private final int code;
  private final String message;

  ErrorCode(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public ErrorResponseBody getErrorResponse() {
    return new ErrorResponseBody(code, message);
  }
}
