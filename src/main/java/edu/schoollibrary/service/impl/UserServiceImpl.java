package edu.schoollibrary.service.impl;

import edu.schoollibrary.entity.AppUser;
import edu.schoollibrary.repository.AppUserRepository;
import edu.schoollibrary.request.CreateUserPojo;
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
    appUser.setEmail(createUserPojo.getEmail());
    appUser.setPassword(createUserPojo.getPassword());
    appUser.setFirstName(createUserPojo.getFirstName());
    appUser.setLastName(createUserPojo.getLastName());
    appUser.setCreatedBy("Admin");
    appUser.setRole("STUDENT");
    appUser.setCreatedAt(new Date());

    appUserRepository.save(appUser);

  }
}
