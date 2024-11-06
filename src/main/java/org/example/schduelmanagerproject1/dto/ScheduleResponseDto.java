package org.example.schduelmanagerproject1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updatedDate;

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
