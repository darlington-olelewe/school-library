package edu.schoollibrary.request;

import lombok.Data;

@Data
public class BookInventoryRequest {
  private Long requesterId;
  private Long bookId;
  private Integer countAdded;
}
