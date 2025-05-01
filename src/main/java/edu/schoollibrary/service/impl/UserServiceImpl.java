package edu.schoollibrary.service.impl;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.exception.AppException;
import edu.schoollibrary.repository.AppUserRepository;
import edu.schoollibrary.request.CreateUserPojo;
import edu.schoollibrary.request.LoginRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
  private final AppUserRepository appUserRepository;

  @Override
  public void createUser(CreateUserPojo createUserPojo) {

    AppUser appUser = new AppUser();
    appUser.setEmail(createUserPojo.getEmail().toLowerCase().trim());
    appUser.setPassword(createUserPojo.getPassword());
    appUser.setFirstName(createUserPojo.getFirstName());
    appUser.setLastName(createUserPojo.getLastName());
    appUser.setCreatedBy("self");
    appUser.setRole("STUDENT");
    appUser.setEnabled(true);
    appUser.setCreatedAt(new Date());

    appUserRepository.save(appUser);

  }

  @Override
  public AppResponse<AppUser> login(LoginRequest loginRequest) {
    AppUser appUser = appUserRepository
        .findAppUserByEmailAndPassword(loginRequest.getEmail().toLowerCase().trim(), loginRequest.getPassword()).orElse(null);

    if(appUser == null){
      throw new AppException("Invalid Email Or Password", "99");
    }
    appUser.setPassword("** hidden **");
    AppResponse<AppUser> appResponse = new AppResponse<>();
    appResponse.setData(appUser);
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
