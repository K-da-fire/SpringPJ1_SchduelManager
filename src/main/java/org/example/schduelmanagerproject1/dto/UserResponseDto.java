package org.example.schduelmanagerproject1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.schduelmanagerproject1.entity.Users;

@Getter
@AllArgsConstructor
public class UserResponseDto {
  private long userId;
  private String email;
  private String name;
  private String password;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updatedDate;

  public UserResponseDto(Users user) {
    this.userId = user.getUserId();
    this.email = user.getEmail();
    this.name = user.getName();
    this.password = user.getPassword();
    this.createdDate = user.getCreatedDate();
    this.updatedDate = user.getUpdatedDate();
  }
}
