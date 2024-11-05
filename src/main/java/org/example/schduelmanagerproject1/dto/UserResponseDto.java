package org.example.schduelmanagerproject1.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserResponseDto {
  private long userId;
  private String email;
  private String name;
  private LocalDate createdDate;
  private LocalDate updatedDate;
}
