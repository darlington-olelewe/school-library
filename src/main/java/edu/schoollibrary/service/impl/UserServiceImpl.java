package edu.schoollibrary.service.impl;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.entity.LoggedInUser;
import edu.schoollibrary.exception.AppException;
import edu.schoollibrary.repository.AppUserRepository;
import edu.schoollibrary.request.CreateUserRequest;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.service.NumberCodec;
import edu.schoollibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final AppUserRepository appUserRepository;
  private final NumberCodec  numberCodec;

  @Override
  public void createUser(CreateUserRequest createUserRequest) {

    AppUser appUser = new AppUser();
    appUser.setEmail(createUserRequest.getEmail().toLowerCase().trim());
    appUser.setPassword(createUserRequest.getPassword());
    appUser.setFirstName(createUserRequest.getFirstName());
    appUser.setLastName(createUserRequest.getLastName());
    appUser.setCreatedBy("self");
    appUser.setRole("STUDENT");
    appUser.setEnabled(true);
    appUser.setCreatedAt(new Date());

    try {
      appUserRepository.save(appUser);
    }catch (Exception e){
      throw new AppException("Error creating user confirm email does not already exists", "99");
    }

  }

  @Override
  public AppResponse<LoggedInUser> login(LoginRequest loginRequest) {
    AppUser appUser = appUserRepository
        .findAppUserByEmailAndPassword(loginRequest.getEmail().toLowerCase().trim(), loginRequest.getPassword()).orElse(null);

    if(appUser == null){
      throw new AppException("Invalid Email Or Password", "99");
    }
    appUser.setPassword("** hidden **");

    LoggedInUser loggedInUser = new LoggedInUser();
    loggedInUser.setPassword(appUser.getPassword());
    loggedInUser.setFirstName(appUser.getFirstName());
    loggedInUser.setLastName(appUser.getLastName());
    loggedInUser.setEmail(appUser.getEmail());
    loggedInUser.setRole(appUser.getRole());
    loggedInUser.setRequestId(numberCodec.encode(appUser.getId()));

    AppResponse<LoggedInUser> appResponse = new AppResponse<>();
    appResponse.setData(loggedInUser);
    appResponse.setCode("00");
    appResponse.setMessage("Success");
    return appResponse;
  }

  @Override
  public AppUser getUserById(Long id) {
    return appUserRepository.findById(id).orElseThrow(() -> new AppException("User not found", "99"));
  }

  @Override
  public void isAdmin(Long id) {
    AppUser user = getUserById(id);
    if(!user.getRole().equalsIgnoreCase("ADMIN")){
      throw new AppException("User is not an admin", "99");
    }
  }

  @Override
  public void isStudent(Long id) {
    AppUser user = getUserById(id);
    if(!user.getRole().equalsIgnoreCase("STUDENT")){
      throw new AppException("User is not a student", "99");
    }
  }
}
