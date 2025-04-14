package edu.schoollibrary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "TBL_BOOK_TRACKER")
public class BookTracker {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private long bookId;
  private long userId;
  private int count;
  private LocalDateTime pickUpDate;
  private LocalDateTime returnDate;

}
