package org.example.schduelmanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {
  public NotFoundException(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
