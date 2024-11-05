package org.example.schduelmanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class NotFoundSchedule extends Exception {

  public NotFoundSchedule(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
