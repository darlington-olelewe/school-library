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
    AppResponse<AppUser> appResponse = new AppResponse<>();
    appResponse.setData(appUser);
    appResponse.setCode("00");
    appResponse.setMessage("Success");
    return appResponse;
  }
}
