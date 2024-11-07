package org.example.schduelmanagerproject1.repository;

import java.util.List;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;

public interface UserRepository {
  UserResponseDto saveUsers(Users users);
  List<UserResponseDto> getAllUsers();

  int updateUser(long id, String name, String email, String password);

  Users getUserById(long id);

  Long getMaxUserId();
  String getPassword(long id);
}
