package org.example.schduelmanagerproject1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ScheduleRequestDto {
  private long userId;
  private String scheduleTitle;
  private String name;
  private String password;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
  private LocalDate updatedDate;
}
