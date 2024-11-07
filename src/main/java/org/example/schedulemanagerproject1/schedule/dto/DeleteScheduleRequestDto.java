package org.example.schedulemanagerproject1.schedule.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class DeleteScheduleRequestDto {
  @NotEmpty(message = "비밀번호는 필수 값입니다.")
  private String password;
}
