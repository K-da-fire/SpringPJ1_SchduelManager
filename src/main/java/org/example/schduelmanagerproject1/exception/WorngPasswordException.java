package org.example.schduelmanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class WorngPasswordException extends Exception {
  public WorngPasswordException(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
