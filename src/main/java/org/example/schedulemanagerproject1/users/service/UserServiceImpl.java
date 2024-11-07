package org.example.schedulemanagerproject1.users.service;

import java.util.List;
import org.example.schedulemanagerproject1.users.dto.UserRequestDto;
import org.example.schedulemanagerproject1.users.dto.UserResponseDto;
import org.example.schedulemanagerproject1.users.entity.Users;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.example.schedulemanagerproject1.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  //유저 생성
  @Override
  public UserResponseDto saveUser(UserRequestDto dto) {
    Users users = new Users(dto.getEmail(), dto.getName(), dto.getPassword(), dto.getCreatedDate());
    return userRepository.saveUsers(users);
  }

  //전체 유저 조회
  @Override
  public List<UserResponseDto> getAllUsers() {
    return userRepository.getAllUsers();
  }

  //선택 유저 조회
  @Override
  public UserResponseDto getUserById(long id) throws NotFoundException {
    Users user = findUser(id);
    return new UserResponseDto(user);
  }

  //선택 유저 수정
  @Transactional
  @Override
  public UserResponseDto updateUser(long id, String name, String email, String password)
      throws NotFoundException, WrongPasswordException {
    checkIdPassword(id, password);
    userRepository.updateUser(id, name, email, password);
    Users user = findUser(id);

    return new UserResponseDto(user);
  }

  //선택 유저 삭제
  @Override
  public void deleteUser(long id, String password)
      throws NotFoundException, WrongPasswordException {
    checkIdPassword(id, password);
    userRepository.deleteUser(id, password);
  }

  //유저 탐색
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

  //id가 존재 여부, 비밀번호 일치 여부 확인
  private void checkIdPassword(long id, String password)
      throws NotFoundException, WrongPasswordException {
    Users user = findUser(id);
    String pw = user.getPassword();
    if(!pw.equals(password)){
      throw new WrongPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
    }
  }

}
