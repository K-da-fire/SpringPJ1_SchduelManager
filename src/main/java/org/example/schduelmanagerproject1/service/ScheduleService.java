package org.example.schduelmanagerproject1.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto);
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate);
  ScheduleResponseDto getScheduleById(long id);
  ScheduleResponseDto updateSchedule(long id, String scheduleTitle, String name, String password);
  void deleteSchedule(long id, String password);
}
