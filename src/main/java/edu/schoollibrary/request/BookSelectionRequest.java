package edu.schoollibrary.request;

import lombok.Data;

@Data
public class BookSelectionRequest {
  private Long requesterId;
  private Long bookId;
}
