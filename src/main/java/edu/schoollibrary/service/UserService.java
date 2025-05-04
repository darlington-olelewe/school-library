package edu.schoollibrary.service;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.entity.LoggedInUser;
import edu.schoollibrary.request.CreateUserRequest;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;

public interface UserService {
  void createUser(CreateUserRequest createUserRequest);
  AppResponse<LoggedInUser> login(LoginRequest loginRequest);
  AppUser getUserById(Long id);
  void isAdmin(Long id);
  void isStudent(Long id);
}
