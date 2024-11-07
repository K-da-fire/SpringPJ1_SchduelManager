package org.example.schedulemanagerproject1.handler;

import java.util.HashMap;
import java.util.Map;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice({"org.example.schduelmanagerproject1.schedule.controller", "org.example.schduelmanagerproject1.users.controller"})
public class GlobalExceptionHandler {

  //잘 못된 정보를 조회 할 때 발생하는 예외
  @ExceptionHandler
  public ResponseEntity<String> notFoundSchedule(NotFoundException ex){
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
  }

  //RequestBody, requsetParam에 id가 숫자형식이 아닐 때 발생하는 예외
  @ExceptionHandler
  public ResponseEntity<String> idFormatException(NumberFormatException ex) {
    return new ResponseEntity<>(/*ex.getMessage()*/"NumberFormatException", HttpStatus.BAD_REQUEST);
  }

  //비밀번호가 틀렸을 때 발생하는 예외
  @ExceptionHandler
  public ResponseEntity<String> checkPassword(WrongPasswordException ex) {
    return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
  }

  //유효성 검사에서 발생한 예외
  @ExceptionHandler
  public ResponseEntity<Map<String, String>> checkInputValid(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("Status", "Fail");
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.put(error.getField(), error.getDefaultMessage()); // 필드 이름과 메시지 매핑
    }

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errors);
  }

}
