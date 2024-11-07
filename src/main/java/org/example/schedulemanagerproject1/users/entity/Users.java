package org.example.schedulemanagerproject1.users.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Users {
  private long userId;
  private String email;
  private String name;
  private String password;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  //유저가 생성될 때 호출되는 생성자
  public Users(String email, String name, String password, LocalDateTime createdDate) {
    this.email = email;
    this.name = (name==null)?"":name;
    this.password = password;
    this.createdDate = (createdDate==null)?LocalDateTime.now():createdDate;
    this.updatedDate = LocalDate.EPOCH.atTime(LocalTime.MIN);
  }
}
