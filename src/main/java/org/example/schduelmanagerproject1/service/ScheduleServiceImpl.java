package org.example.schduelmanagerproject1.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;
import org.example.schduelmanagerproject1.repository.ScheduleRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    Schedule schedule = new Schedule(dto.getUserId(),dto.getTodoList(), dto.getName(), dto.getPassword(), dto.getCreatedDate());

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
  public ScheduleResponseDto updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException {
    checkIdPassword(id,password); //id가 존재하고, password가 일치 하는지 확인
    scheduleRepository.updateSchedule(id, todoList, name, password); //password가 일치하므로 업데이트를 진행한다.
    Schedule schedule = findSchedule(id);  //업데이트 후 반환 값을 조회

    return new ScheduleResponseDto(schedule);
  }

  @Override
  public void deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException {
    checkIdPassword(id, password);
    scheduleRepository.deleteSchedule(id, password);
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

  private void checkIdPassword(long id, String password)
      throws NotFoundException, WrongPasswordException {
    findSchedule(id); //id가 존재할 때 실행 이 단계를 넘으면 아이디가 존재 한다는 의미
    String psw = scheduleRepository.getPassword(id);
    if(!psw.equals(password)){
      throw new WrongPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
    }
  }

  
}
