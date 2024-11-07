package org.example.schduelmanagerproject1.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleRequestDto {
  private long userId;
  @NotNull(message = "할 일은 필수값 입니다.")
  @Size(max = 200, message = "일정은 200자 이내입니다.")
  private String todoList;
  private String name;
  @NotNull(message = "비밀번호는 필수값 입니다.")
  private String password;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime createdDate;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
  private LocalDateTime updatedDate;
}
