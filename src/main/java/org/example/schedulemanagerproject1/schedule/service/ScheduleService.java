package org.example.schedulemanagerproject1.schedule.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleRequestDto;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto)
      throws NotFoundException;
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable)
      throws NotFoundException;
  ScheduleResponseDto getScheduleById(long id)
      throws NotFoundException;
  ScheduleResponseDto updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException;
  void deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException;
}
