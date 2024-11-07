package org.example.schedulemanagerproject1.schedule.service;

import java.time.LocalDate;
import java.util.List;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleRequestDto;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleService {
  //일정 생성
  ScheduleResponseDto saveSchedule(ScheduleRequestDto dto)
      throws NotFoundException;
  //전체 일정 조회
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable)
      throws NotFoundException;
  //선택 일정 조회
  ScheduleResponseDto getScheduleById(long id)
      throws NotFoundException;
  //선택 일정 수정
  ScheduleResponseDto updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException;
  //선택 일정 삭제
  void deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException;
}
