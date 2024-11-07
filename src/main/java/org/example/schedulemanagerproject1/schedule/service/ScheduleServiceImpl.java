package org.example.schedulemanagerproject1.schedule.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleRequestDto;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.schedule.entity.Schedule;
import org.example.schedulemanagerproject1.users.entity.Users;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.example.schedulemanagerproject1.schedule.repository.ScheduleRepository;
import org.example.schedulemanagerproject1.users.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;
  private final UserRepository userRepository;

  public ScheduleServiceImpl(ScheduleRepository scheduleRepository, UserRepository userRepository) {
    this.scheduleRepository = scheduleRepository;
    this.userRepository = userRepository;
  }

  //일정 생성
  @Override
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto)
      throws NotFoundException {
    findUser(dto.getUserId());
    Schedule schedule = new Schedule(dto.getUserId(),dto.getTodoList(), dto.getName(), dto.getPassword(), dto.getCreatedDate());

    return scheduleRepository.saveSchedule(schedule);
  }

  //전체 일정 조회
  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate,
      Pageable pageable) throws NotFoundException {
    findUser(userId);
    return scheduleRepository.getAllSchedules(userId, name, updatedDate, pageable);
  }

  //선택 일정 조회
  @Override
  public ScheduleResponseDto getScheduleById(long id) throws NotFoundException {
    Schedule schedule = findSchedule(id);
    return new ScheduleResponseDto(schedule);
  }

  //선택 일정 수정
  @Transactional
  @Override
  public ScheduleResponseDto updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException {
    checkIdPassword(id,password); //id가 존재하고, password가 일치 하는지 확인
    scheduleRepository.updateSchedule(id, todoList, name, password); //password가 일치하므로 업데이트를 진행한다.
    Schedule schedule = findSchedule(id);  //업데이트 후 반환 값을 조회

    return new ScheduleResponseDto(schedule);
  }

  //선택 일정 수정
  @Override
  public void deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException {
    checkIdPassword(id, password);
    scheduleRepository.deleteSchedule(id, password);
  }

  //유저 조회
  private void findUser(long userId) throws NotFoundException {
    Users user = userRepository.getUserById(userId);
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

  //id 존재 여부, 비밀번호 일치 확인
  private void checkIdPassword(long id, String password)
      throws NotFoundException, WrongPasswordException {
    Schedule schedule = findSchedule(id); //id가 존재할 때 실행 이 단계를 넘으면 아이디가 존재 한다는 의미
    String psw = schedule.getPassword();
    if(!psw.equals(password)){
      throw new WrongPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
    }
  }

}
