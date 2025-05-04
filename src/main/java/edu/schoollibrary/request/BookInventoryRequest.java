package edu.schoollibrary.request;

import lombok.Data;

@Data
public class BookInventoryRequest {
  private String requesterId;
  private Long bookId;
  private Integer countAdded;
}
