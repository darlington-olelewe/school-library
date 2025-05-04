package edu.schoollibrary.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Base {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public Base(){}
  public Base(Long id){
    this.id = id;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {this.id = id;}
}
