package edu.schoollibrary.controller;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.request.CreateUserPojo;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.response.CodeAndMessage;
import edu.schoollibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user-management")
@RequiredArgsConstructor
public class AppUserController {
  private final UserService userService;


  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public AppResponse<AppUser> loginUser(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/sign-up")
  public CodeAndMessage createUser(@RequestBody CreateUserPojo createUserPojo) {
    userService.createUser(createUserPojo);
    return new CodeAndMessage("00", "User created successfully");
  }
}
