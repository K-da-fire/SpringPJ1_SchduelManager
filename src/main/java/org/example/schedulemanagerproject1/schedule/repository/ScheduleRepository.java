package org.example.schedulemanagerproject1.schedule.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.schedule.entity.Schedule;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable);
  Schedule getScheduleById(long id);
  int updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException;
  int deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException;

  String getMaxScheduleId();
}
