package org.example.schduelmanagerproject1.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate);
  Schedule getScheduleByIdOrElseThrow(long id);
  int updateSchedule(long id, String scheduleTitle, String name, String password);
  int deleteSchedule(long id, String password);
}
