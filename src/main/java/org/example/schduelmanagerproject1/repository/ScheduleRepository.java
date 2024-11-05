package org.example.schduelmanagerproject1.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.exception.DeletedSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundUser;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule) throws DeletedSchedule, NotFoundUser;
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable);
  Schedule getScheduleByIdOrElseThrow(long id) throws DeletedSchedule, NotFoundSchedule;
  int updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, DeletedSchedule;
  int deleteSchedule(long id, String password) throws WorngPasswordException, DeletedSchedule;
}
