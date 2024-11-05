package org.example.schduelmanagerproject1.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.exception.DeletedSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundUser;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.example.schduelmanagerproject1.service.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

  private final ScheduleService scheduleService;

  public ScheduleController(ScheduleService scheduleService) {
    this.scheduleService = scheduleService;
  }

  //일정 생성
  @PostMapping
  public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody @Valid ScheduleRequestDto dto)
      throws DeletedSchedule, NotFoundUser {
    return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
  }

  //전체 일정 조회
  @GetMapping
  public List<ScheduleResponseDto> getAllSchedules(@RequestBody ScheduleRequestDto dto, final Pageable pageable) {
    System.out.println(pageable.toString());
    return scheduleService.getAllSchedules(dto.getUserId(), dto.getName(), dto.getUpdatedDate(), pageable);
  }

  //선택 일정 조회
  @GetMapping(value = "/{id}")
  public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable long id)
      throws DeletedSchedule, NotFoundSchedule {
    return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> updateSchedule(
      @PathVariable long id,
      @RequestBody ScheduleRequestDto dto)
      throws WorngPasswordException, DeletedSchedule, NotFoundSchedule {
    return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getScheduleTitle(), dto.getName(), dto.getPassword()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(
      @PathVariable long id,
      @RequestBody ScheduleRequestDto dto
      ) throws WorngPasswordException, DeletedSchedule {
    scheduleService.deleteSchedule(id, dto.getPassword());
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @ExceptionHandler
  public ResponseEntity<String> checkIdPassword(WorngPasswordException ex) {
    return new ResponseEntity<>("id 혹은 비밀번호를 확인하세요 controller", HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler
  public ResponseEntity<String> notFoundUser(NotFoundUser ex){
    return new ResponseEntity<>("등록되지 않은 유저입니다. controller", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public ResponseEntity<String> DeletedSchedule(DeletedSchedule ex){
    return new ResponseEntity<>("삭제된 일정입니다. controller", HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler
  public  ResponseEntity<String> notFoundSchedule(NotFoundSchedule ex){
    return new ResponseEntity<>("일정을 찾을 수 없습니다. controller", HttpStatus.NOT_FOUND);
  }

}
