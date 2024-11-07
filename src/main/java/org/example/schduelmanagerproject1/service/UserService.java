package org.example.schduelmanagerproject1.service;

import org.example.schduelmanagerproject1.dto.UserRequestDto;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import java.util.List;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;

public interface UserService {
  UserResponseDto saveUser(UserRequestDto dto);

  List<UserResponseDto> getAllUsers();

  UserResponseDto getUserById(long id) throws NotFoundException;

  UserResponseDto updateUser(long id, String name, String email, String password)
      throws NotFoundException, WrongPasswordException;
}
