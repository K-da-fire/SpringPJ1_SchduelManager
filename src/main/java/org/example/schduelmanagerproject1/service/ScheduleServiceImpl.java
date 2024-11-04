package org.example.schduelmanagerproject1.service;

import java.util.List;
import java.util.Optional;
import org.example.schduelmanagerproject1.dto.ScheduleRequestDto;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

  private final ScheduleRepository scheduleRepository;

  public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
    this.scheduleRepository = scheduleRepository;
  }

  @Override
  public ScheduleResponseDto saveSchedule(ScheduleRequestDto dto) {
    Schedule schedule = new Schedule(dto.getUserId(),dto.getScheduleTitle(), dto.getName(), dto.getPassword(), dto.getCreatedDate());

    return scheduleRepository.saveSchedule(schedule);
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules() {
    return scheduleRepository.getAllSchedules();
  }

  @Override
  public ScheduleResponseDto getScheduleById(Long id) {
    Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
    return new ScheduleResponseDto(schedule);
  }


}
