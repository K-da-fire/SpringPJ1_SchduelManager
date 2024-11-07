package org.example.schedulemanagerproject1.users.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class DeleteUsersRequestDto {
  @NotNull(message = "비밀번호는 필수 값입니다.")
  private String password;
}
