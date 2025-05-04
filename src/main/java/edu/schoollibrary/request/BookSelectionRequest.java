package edu.schoollibrary.request;

import lombok.Data;

@Data
public class BookSelectionRequest {
  private String requesterId;
  private Long bookId;
}
