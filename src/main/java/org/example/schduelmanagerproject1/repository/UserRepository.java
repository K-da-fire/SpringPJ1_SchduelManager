package org.example.schduelmanagerproject1.repository;

import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;

public interface UserRepository {
  UserResponseDto saveUsers(Users users);
}
