package org.example.schduelmanagerproject1.service;

import java.util.List;
import org.example.schduelmanagerproject1.dto.UserRequestDto;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;
import org.example.schduelmanagerproject1.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserResponseDto saveUser(UserRequestDto dto) {
    Users users = new Users(dto.getEmail(), dto.getName(), dto.getPassword(), dto.getCreatedDate());
    return userRepository.saveUsers(users);
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    return userRepository.getAllUsers();
  }

  @Override
  public UserResponseDto getUserById(long id) throws NotFoundException {
    Users user = findUser(id);
    return new UserResponseDto(user);
  }

  @Override
  public UserResponseDto updateUser(long id, String name, String email, String password)
      throws NotFoundException, WrongPasswordException {
    checkIdPassword(id, password);
    userRepository.updateUser(id, name, email, password);
    Users user = findUser(id);

    return new UserResponseDto(user);
  }

  private Users findUser(long id) throws NotFoundException {
    long userId = userRepository.getMaxUserId();
    Users user = userRepository.getUserById(id);
    if(user == null){
      if(id < userId){
        throw new NotFoundException(HttpStatus.NOT_FOUND, "삭제된 유저입니다.");
      }
      throw new NotFoundException(HttpStatus.NOT_FOUND, "등록되지 않은 유저입니다.");
    }
    return user;
  }

  private void checkIdPassword(long id, String password)
      throws NotFoundException, WrongPasswordException {
    findUser(id);
    String pw = userRepository.getPassword(id);
    if(!pw.equals(password)){
      throw new WrongPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
    }
  }

}
