package edu.schoollibrary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "TBL_APP_USER")
@Data
public class AppUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, name = "first_name")
  private String firstName;
  @Column(nullable = false, name = "last_name")
  private String lastName;
  @Column(nullable = false, name = "email", unique = true)
  private String email;
  private String password;
  @Column(nullable = false)
  private String role;
  private boolean enabled;
  private String createdBy;
  private Date createdAt;
}
