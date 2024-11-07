package org.example.schduelmanagerproject1.controller;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.example.schduelmanagerproject1.service.ScheduleService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
      throws NotFoundException {
    return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
  }

  //전체 일정 조회
  @GetMapping
  public List<ScheduleResponseDto> getAllSchedules(
      @RequestParam String userId,
      @RequestParam(required = false) String name,
      @RequestParam(required = false) LocalDate updatedDate,
      final Pageable pageable)
      throws NotFoundException {
    long id = Long.parseLong(userId);
    return scheduleService.getAllSchedules(id, name, updatedDate, pageable);
  }

  //선택 일정 조회
  @GetMapping(value = "/{id}")
  public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable long id)
      throws NotFoundException {
    return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> updateSchedule(
      @PathVariable long id,
      @RequestBody @Valid ScheduleRequestDto dto)
      throws WorngPasswordException, NotFoundException {
    return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getScheduleTitle(), dto.getName(), dto.getPassword()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> deleteSchedule(
      @PathVariable long id,
      @RequestBody Map<String, String> password
      ) throws WorngPasswordException, NotFoundException {
    if(password==null || password.isEmpty()){
      return new ResponseEntity<>("패스워드를 입력해주세요.", HttpStatus.BAD_REQUEST);
    }
    scheduleService.deleteSchedule(id, password.get("password"));
    return new ResponseEntity<>("일정을 삭제하였습니다.", HttpStatus.OK);
  }
}
