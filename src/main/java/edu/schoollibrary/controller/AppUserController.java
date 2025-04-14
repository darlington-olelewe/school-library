package edu.schoollibrary.controller;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.request.CreateUserPojo;
import edu.schoollibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user-management")
@RequiredArgsConstructor
public class AppUserController {
  private final UserService userService;

  @PostMapping("/create-user")
  public ResponseEntity<String> createUser(@RequestBody CreateUserPojo createUserPojo) {
    userService.createUser(createUserPojo);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
