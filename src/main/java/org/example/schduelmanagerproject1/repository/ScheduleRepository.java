package org.example.schduelmanagerproject1.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable);
  Schedule getScheduleById(long id);
  int updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException;
  int deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException;

  String getPassword(long id);
  Users getUser(long id);
//  Schedule getSchedule(long id);
  String getMaxScheduleId();
}
