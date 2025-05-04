package edu.schoollibrary.request;

import lombok.Data;

@Data
public class BookRequest {
  private String requesterId;
  private String title;
  private String authors;
  private String isbn;
  private String review;
  private int year;
  private int pages;
}
