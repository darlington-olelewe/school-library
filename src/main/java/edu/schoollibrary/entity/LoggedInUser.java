package edu.schoollibrary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;


@Data
public class LoggedInUser {
  private String requestId;
  private String firstName;
  private String lastName;
  private String email;
  private String password;
  private String role;
  private boolean enabled;
  private String createdBy;
  private Date createdAt;
}
