package edu.schoollibrary.request;


import lombok.Data;

@Data
public class CreateUserPojo {
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String role;
}
