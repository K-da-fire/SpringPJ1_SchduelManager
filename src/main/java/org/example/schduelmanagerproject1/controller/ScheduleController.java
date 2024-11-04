package org.example.schduelmanagerproject1.controller;

import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

  @PostMapping
  public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto dto) {
    return new ResponseEntity<>(scheduleService.saveSchedule(dto), HttpStatus.CREATED);
  }

}
