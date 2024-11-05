package org.example.schduelmanagerproject1.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schduelmanagerproject1.entity.Schedule;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
  private long scheduleId;
  private long userId;
  private String ScheduleTitle;
  private String name;
  private String password;
  private LocalDate createdDate;
  private LocalDate updatedDate;

  public ScheduleResponseDto(Schedule schedule) {
    this.scheduleId = schedule.getScheduleId();
    this.userId = schedule.getUserId();
    this.ScheduleTitle = schedule.getScheduleTitle();
    this.name = schedule.getName();
    this.password = schedule.getPassword();
    this.createdDate = schedule.getCreatedDate();
    this.updatedDate = schedule.getUpdatedDate();
  }

}
