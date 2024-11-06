package org.example.schduelmanagerproject1.controller;

import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice("org.example.schduelmanagerproject1.controller")
public class ExceptionController {

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<String> notFoundSchedule(Exception ex){
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<String> InvalidException(NumberFormatException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler
  public ResponseEntity<String> checkIdPassword(WorngPasswordException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler
  public ResponseEntity<String> checkIdPassword(MethodArgumentNotValidException ex) {
    //+"는 "+ex.getBindingResult().getObjectName() + "의 필수 값입니다."
//    System.out.println("\n\n\n"+ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    return new ResponseEntity<>(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage(), HttpStatus.NOT_FOUND);
  }

//  @ExceptionHandler
//  public ResponseEntity<String> tooLongValue(DataIntegrityViolationException ex) {
//    System.out.println("tooLongValue : "+ex.getMessage());
//    return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
//  }

}
