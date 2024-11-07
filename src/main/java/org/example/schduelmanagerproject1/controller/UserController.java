package org.example.schduelmanagerproject1.controller;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.example.schduelmanagerproject1.dto.UserRequestDto;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.exception.NotFoundException;
import org.example.schduelmanagerproject1.exception.WrongPasswordException;
import org.example.schduelmanagerproject1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<UserResponseDto> createdUsers(@RequestBody @Valid UserRequestDto dto) {
    return new ResponseEntity<>(userService.saveUser(dto), HttpStatus.CREATED);
  }

  @GetMapping
  public List<UserResponseDto> getAllUsers() {
    return userService.getAllUsers();
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable long id)
      throws NotFoundException {
    return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<UserResponseDto> updatedUsers(
      @PathVariable long id,
      @RequestBody @Valid UserRequestDto dto) throws NotFoundException, WrongPasswordException {
  return new ResponseEntity<>(userService.updateUser(id, dto.getName(), dto.getEmail(), dto.getPassword()), HttpStatus.OK);
  }

}
