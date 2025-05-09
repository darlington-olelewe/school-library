package edu.schoollibrary.controller;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.entity.LoggedInUser;
import edu.schoollibrary.request.CreateUserRequest;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.response.CodeAndMessage;
import edu.schoollibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user-management")
@RequiredArgsConstructor
public class AppUserController {
  private final UserService userService;


  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/login")
  public AppResponse<LoggedInUser> loginUser(@RequestBody LoginRequest loginRequest) {
    return userService.login(loginRequest);
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/sign-up")
  public CodeAndMessage createUser(@RequestBody CreateUserRequest createUserRequest) {
    userService.createUser(createUserRequest);
    return new CodeAndMessage("00", "User created successfully");
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/fetch-all-users/{requester-id}")
  public AppResponse<List<LoggedInUser>> fetchAllUsers(@PathVariable("requester-id") String requesterId) {
    return userService.allUsers(requesterId);
  }
}
