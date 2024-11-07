package org.example.schduelmanagerproject1.controller;

import java.util.HashMap;
import java.util.Map;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice("org.example.schduelmanagerproject1.controller")
public class ExceptionController {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<String> notFoundSchedule(Exception ex){
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<String> PasswordFormatException(NumberFormatException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<String> checkPassword(WrongPasswordException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler
  public ResponseEntity<Map<String, String>> checkInputValid(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("Status", "Fail");
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage()); // 필드 이름과 메시지 매핑
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

  @ExceptionHandler
  public ResponseEntity<String> ResponseStateException(ResponseStatusException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
