package org.example.schedulemanagerproject1.users.repository;

import java.util.List;
import org.example.schedulemanagerproject1.users.dto.UserResponseDto;
import org.example.schedulemanagerproject1.users.entity.Users;

public interface UserRepository {
  //유저 생성
  UserResponseDto saveUsers(Users users);
  //전체 유저 조회
  List<UserResponseDto> getAllUsers();
  //선택 유저 조회
  Users getUserById(long id);
  //선택 유저 수정
  void updateUser(long id, String name, String email, String password);
  //선택 유저 삭제
  void deleteUser(long id, String password);

  Long getMaxUserId();
}
