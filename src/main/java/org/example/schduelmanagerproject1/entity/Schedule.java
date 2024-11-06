package org.example.schduelmanagerproject1.entity;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
  private long scheduleId;      //PK
  private long userId;          //FK
  private String scheduleTitle; //NOT NULL
  private String name;
  private String password;      //NOT NULL
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  //일정이 생성될 때 사용되는 생성자
  public Schedule(long userId, String scheduleTitle, String name, String password, LocalDateTime createdDate) {
    this.userId = userId;
    this.scheduleTitle = scheduleTitle;
    this.name = (name==null)?"":name;
    this.password = password;
    this.createdDate = (createdDate==null)?LocalDateTime.now():createdDate;
    this.updatedDate = LocalDate.EPOCH.atTime(LocalTime.MIN);
  }

}
