package edu.schoollibrary.service;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.request.CreateUserPojo;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;

public interface UserService {
  void createUser(CreateUserPojo createUserPojo);
  AppResponse<AppUser> login(LoginRequest loginRequest);
  AppUser getUserById(Long id);
  void isAdmin(Long id);
  void isStudent(Long id);
}
