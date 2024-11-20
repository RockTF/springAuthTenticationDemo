package com.altimetrik.ee.demo.exception;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private String message;
  private HttpStatus httpStatus;

  public CustomException() {
    super();
  }

  public CustomException(String message, HttpStatus httpStatus) {
    super(message);
    this.message = message;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
