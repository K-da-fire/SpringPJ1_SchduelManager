package org.example.schduelmanagerproject1.service;

import java.util.List;
import java.util.Optional;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
  List<ScheduleResponseDto> getAllSchedules();
  ScheduleResponseDto getScheduleById(Long id);
  ScheduleResponseDto updateSchedule(long id, String scheduleTitle, String name);
  void deleteSchedule(long id);
}
