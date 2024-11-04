package org.example.schduelmanagerproject1.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

//  public ScheduleResponseDto(long id, String scheduleTitle, String name, String password, String createdDate) {
//    this.id = id;
//    this.ScheduleTitle = scheduleTitle;
//    this.name = name;
//    this.password = password;
//    this.createdDate = createdDate;
//  }

}
