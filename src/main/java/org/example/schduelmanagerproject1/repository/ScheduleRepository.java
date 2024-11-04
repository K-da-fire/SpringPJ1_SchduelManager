package org.example.schduelmanagerproject1.repository;

import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);
  List<ScheduleResponseDto> getAllSchedules();
  Schedule getScheduleByIdOrElseThrow(Long id);
  int updateSchedule(long id, String scheduleTitle, String name);
  int deleteSchedule(long id);
}
