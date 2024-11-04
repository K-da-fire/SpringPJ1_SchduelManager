package org.example.schduelmanagerproject1.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {
  private long scheduleId;
  private long userId;
  private String scheduleTitle;
  private String name;
  private String password;
  private LocalDate createdDate;
  private LocalDate updatedDate;

  public Schedule(long userId, String scheduleTitle, String name, String password, LocalDate createdDate){
    this.userId = userId;
    this.scheduleTitle = scheduleTitle;
    this.name = name;
    this.password = password;
    this.createdDate = createdDate;
  }

  public void update(String scheduleTitle, String name, String password, LocalDate updatedDate){
    if(this.password.equals(password)) {
      this.scheduleTitle = scheduleTitle;
      this.name = name;
      this.updatedDate = updatedDate;
    }
  }

}
