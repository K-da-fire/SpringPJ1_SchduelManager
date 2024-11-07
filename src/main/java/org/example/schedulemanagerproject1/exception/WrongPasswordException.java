package org.example.schedulemanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class WrongPasswordException extends Exception {
  public WrongPasswordException(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
