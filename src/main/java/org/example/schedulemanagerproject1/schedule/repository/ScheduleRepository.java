package org.example.schedulemanagerproject1.schedule.repository;

import java.time.LocalDate;
import java.util.List;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.schedule.entity.Schedule;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.springframework.data.domain.Pageable;

public interface ScheduleRepository {
  //일정 생성
  ScheduleResponseDto saveSchedule(Schedule schedule);
  //일정 전체 조회
  List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable);
  //선택 일정 조회
  Schedule getScheduleById(long id);
  //선택 일정 수정
  int updateSchedule(long id, String todoList, String name, String password)
      throws WrongPasswordException, NotFoundException;
  //선택 일정 삭제
  int deleteSchedule(long id, String password)
      throws WrongPasswordException, NotFoundException;

  String getMaxScheduleId();
}
