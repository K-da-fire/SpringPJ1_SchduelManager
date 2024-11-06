package org.example.schduelmanagerproject1.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule)
      throws NotFoundException;
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable)
      throws NotFoundException;
  Schedule getScheduleByIdOrElseThrow(long id)
      throws NotFoundException;
  int updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, NotFoundException;
  int deleteSchedule(long id, String password)
      throws WorngPasswordException, NotFoundException;
}
