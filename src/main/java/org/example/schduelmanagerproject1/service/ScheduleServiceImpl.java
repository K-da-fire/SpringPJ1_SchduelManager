package org.example.schduelmanagerproject1.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.exception.DeletedSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundUser;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.example.schduelmanagerproject1.repository.ScheduleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;

  public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  @Override
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) throws DeletedSchedule, NotFoundUser {
    Schedule schedule = new Schedule(dto.getUserId(),dto.getScheduleTitle(), dto.getName(), dto.getPassword(), dto.getCreatedDate());

    return scheduleRepository.saveSchedule(schedule);
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate,
      Pageable pageable) {
    return scheduleRepository.getAllSchedules(userId, name, updatedDate, pageable);
  }

  @Override
  public ScheduleResponseDto getScheduleById(long id) throws DeletedSchedule, NotFoundSchedule {
    Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(schedule);
  }

  @Transactional
  @Override
  public ScheduleResponseDto updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, DeletedSchedule, NotFoundSchedule {
    if(scheduleTitle == null || name == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents can't be null");
    }

    int updateRow = scheduleRepository.updateSchedule(id, scheduleTitle, name, password);

    if(updateRow == 0) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }

    Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

    return new ScheduleResponseDto(schedule);
  }

  @Override
  public void deleteSchedule(long id, String password)
      throws WorngPasswordException, DeletedSchedule {
    int deleteRow = scheduleRepository.deleteSchedule(id, password);
    if(deleteRow == 0){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }
  }


}
