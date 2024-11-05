package org.example.schduelmanagerproject1.exception;

import org.springframework.http.HttpStatus;

public class DeletedSchedule extends Exception {

  public DeletedSchedule(HttpStatus rawStatusCode, String message) {
    super(message);
  }
}
