package org.example.schduelmanagerproject1.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.exception.NotFoundException;
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
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto)
      throws NotFoundException {
    findUser(dto.getUserId());
    Schedule schedule = new Schedule(dto.getUserId(),dto.getScheduleTitle(), dto.getName(), dto.getPassword(), dto.getCreatedDate());

    return scheduleRepository.saveSchedule(schedule);
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate,
      Pageable pageable) throws NotFoundException {
    findUser(userId);
    return scheduleRepository.getAllSchedules(userId, name, updatedDate, pageable);
  }

  @Override
  public ScheduleResponseDto getScheduleById(long id) throws NotFoundException {
    Schedule schedule = findSchedule(id);
    return new ScheduleResponseDto(schedule);
  }

  @Transactional
  @Override
  public ScheduleResponseDto updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, NotFoundException {
    if(scheduleTitle == null || name == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title and contents can't be null");
    }
    checkPassword(id,password);
    scheduleRepository.updateSchedule(id, scheduleTitle, name, password);
    Schedule schedule = findSchedule(id);

    return new ScheduleResponseDto(schedule);
  }

  @Override
  public void deleteSchedule(long id, String password)
      throws WorngPasswordException, NotFoundException {
    checkPassword(id, password);
    int deleteRow = scheduleRepository.deleteSchedule(id, password);
    if(deleteRow == 0){
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
    }
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////
  private void findUser(long id) throws NotFoundException {
    Users user = scheduleRepository.getUser(id);
    if(user == null) throw new NotFoundException(HttpStatus.NOT_FOUND, "등록되지않은 유저입니다.");
  }

  //널체크를 하고, 등록된 일정을 반환해주는 함수
  private Schedule findSchedule(long id) throws NotFoundException {
    long scheduleId = Long.parseLong(scheduleRepository.getMaxScheduleId());
    Schedule schedule = scheduleRepository.getScheduleById(id);
    if(schedule == null){
      if(id < scheduleId){
        throw new NotFoundException(HttpStatus.NOT_FOUND, "삭제된 일정입니다.");
      }
      throw new NotFoundException(HttpStatus.NOT_FOUND, "등록되지 않은 일정입니다.");
    }
    return schedule;
  }

  private void checkPassword(long id, String password)
      throws NotFoundException, WorngPasswordException {
    findSchedule(id); //id가 존재할 때 실행 이 단계를 넘으면 아이디가 존재 한다는 의미
    String psw = scheduleRepository.getPassword(id);
    if(!psw.equals(password)){
      throw new WorngPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
    }
  }

  
}
