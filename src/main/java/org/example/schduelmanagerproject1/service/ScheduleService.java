package org.example.schduelmanagerproject1.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.exception.DeletedSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundUser;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) throws DeletedSchedule, NotFoundUser;
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable);
  ScheduleResponseDto getScheduleById(long id) throws DeletedSchedule, NotFoundSchedule;
  ScheduleResponseDto updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, DeletedSchedule, NotFoundSchedule;
  void deleteSchedule(long id, String password) throws WorngPasswordException, DeletedSchedule;
}
