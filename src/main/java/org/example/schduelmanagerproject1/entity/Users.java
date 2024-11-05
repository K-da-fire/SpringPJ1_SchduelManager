package org.example.schduelmanagerproject1.entity;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Users {
  private long userId;
  private String email;
  private String name;
  private LocalDate createdDate;
  private LocalDate updatedDate;

  public Users(String email, String name, LocalDate createdDate) {
    this.email = email;
    this.name = name;
    this.createdDate = createdDate;
  }
}
