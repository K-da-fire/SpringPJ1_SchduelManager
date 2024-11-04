package org.example.schduelmanagerproject1.repository;

import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;

public interface ScheduleRepository {
  ScheduleResponseDto saveSchedule(Schedule schedule);

}
