package org.example.schduelmanagerproject1.entity;

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

  public Users(String email, String name, String password, LocalDateTime createdDate) {
    this.email = email;
    this.name = (name==null)?"":name;
    this.password = password;
    this.createdDate = (createdDate==null)?LocalDateTime.now():createdDate;
    this.updatedDate = LocalDate.EPOCH.atTime(LocalTime.MIN);
  }

  public Users(long userId, String email, String name) {
    this.userId = userId;
    this.email = email;
    this.name = name;
  }
}
