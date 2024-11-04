package org.example.schduelmanagerproject1.service;

import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
}
