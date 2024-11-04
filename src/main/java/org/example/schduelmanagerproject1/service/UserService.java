package org.example.schduelmanagerproject1.service;

import org.example.schduelmanagerproject1.dto.UserRequestDto;
import org.example.schduelmanagerproject1.dto.UserResponseDto;

public interface UserService {
  UserResponseDto saveUser(UserRequestDto dto);
}
