package org.example.schedulemanagerproject1.users.service;

import org.example.schedulemanagerproject1.users.dto.UserRequestDto;
import org.example.schedulemanagerproject1.users.dto.UserResponseDto;
import java.util.List;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;

public interface UserService {
  //우저 생성
  UserResponseDto saveUser(UserRequestDto dto);
  //전체 일정 조회
  List<UserResponseDto> getAllUsers();
  //선택 일정 조회
  UserResponseDto getUserById(long id) throws NotFoundException;
  //선택 일정 수정
  UserResponseDto updateUser(long id, String name, String email, String password) throws NotFoundException, WrongPasswordException;
  //선택 일정 삭제
  void deleteUser(long id, String password) throws NotFoundException, WrongPasswordException;
}
