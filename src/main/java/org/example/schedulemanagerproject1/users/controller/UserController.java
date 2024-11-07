package org.example.schedulemanagerproject1.users.controller;

import jakarta.validation.Valid;
import java.util.List;
import org.example.schedulemanagerproject1.users.dto.DeleteUsersRequestDto;
import org.example.schedulemanagerproject1.users.dto.UserRequestDto;
import org.example.schedulemanagerproject1.users.dto.UserResponseDto;
import org.example.schedulemanagerproject1.exception.NotFoundException;
import org.example.schedulemanagerproject1.exception.WrongPasswordException;
import org.example.schedulemanagerproject1.users.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  //유저 생성
  @PostMapping
  public ResponseEntity<UserResponseDto> createdUsers(@RequestBody @Valid UserRequestDto dto) {
    return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
  }

  //유저 전체 조회
  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return userService.getAllUsers();
  }

  //선택 유저 조회
  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id)
      throws NotFoundException {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  //선택 유저 수정
  @PatchMapping("/{id}")
  public ResponseEntity<UserResponseDto> updatedUsers(
      @PathVariable long id,
      @RequestBody @Valid UserRequestDto dto) throws NotFoundException, WrongPasswordException {
  return new ResponseEntity<>(userService.updateUser(id, dto.getName(), dto.getEmail(), dto.getPassword()), HttpStatus.OK);
  }

  //선택 유저 삭제
  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable long id, @RequestBody @Valid DeleteUsersRequestDto dto)
      throws NotFoundException, WrongPasswordException {
    userService.deleteUser(id, dto.getPassword());
  }
}
