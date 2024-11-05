package org.example.schduelmanagerproject1.entity;

import java.time.LocalDate;
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
  private LocalDate createdDate;
  private LocalDate updatedDate;

  //일정이 생성될 때 사용되는 생성자
  public Schedule(long userId, String scheduleTitle, String name, String password, LocalDate createdDate) {
    this.userId = userId;
    this.scheduleTitle = scheduleTitle;
    this.name = (name==null)?"":name;
    this.password = password;
    this.createdDate = (createdDate==null)?LocalDate.now():createdDate;
    this.updatedDate = LocalDate.EPOCH;
  }

}
