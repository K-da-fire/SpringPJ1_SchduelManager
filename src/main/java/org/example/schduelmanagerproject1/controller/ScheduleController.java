package org.example.schduelmanagerproject1.controller;

import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
  public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
    return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
  }

  //전체 일정 조회
  @GetMapping
  public List<ScheduleResponseDto> getAllSchedules(@RequestBody ScheduleRequestDto dto) {
    return scheduleService.getAllSchedules(dto.getUserId(), dto.getName(), dto.getUpdatedDate());
  }

  //선택 일정 조회
  @GetMapping(value = "/{id}")
  public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable long id) {
    return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ScheduleResponseDto> updateSchedule(
      @PathVariable long id,
      @RequestBody ScheduleRequestDto dto) {
    return new ResponseEntity<>(scheduleService.updateSchedule(id, dto.getScheduleTitle(), dto.getName(), dto.getPassword()), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchedule(
      @PathVariable long id,
      @RequestBody ScheduleRequestDto dto
      ) {
    scheduleService.deleteSchedule(id, dto.getPassword());
    return new ResponseEntity<>(HttpStatus.OK);
  }

}
