package org.example.schduelmanagerproject1.service;

import org.example.schduelmanagerproject1.dto.UserRequestDto;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResponseDto saveUser(UserRequestDto dto) {
    Users users = new Users(dto.getEmail(), dto.getPassword(), dto.getName(), dto.getCreatedDate());
    return userRepository.saveUsers(users);
  }
}
