package org.example.schduelmanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class NotFoundUser extends Exception {

  public NotFoundUser(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
