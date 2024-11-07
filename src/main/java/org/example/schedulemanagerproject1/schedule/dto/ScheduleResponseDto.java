package org.example.schedulemanagerproject1.schedule.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schedulemanagerproject1.schedule.entity.Schedule;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {
  private long scheduleId;
  private long userId;
  private String todoList;
  private String name;
  private String password;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updatedDate;

  public ScheduleResponseDto(Schedule schedule) {
    this.scheduleId = schedule.getScheduleId();
    this.userId = schedule.getUserId();
    this.todoList = schedule.getTodoList();
    this.name = schedule.getName();
    this.password = schedule.getPassword();
    this.createdDate = schedule.getCreatedDate();
    this.updatedDate = schedule.getUpdatedDate();
  }

}
