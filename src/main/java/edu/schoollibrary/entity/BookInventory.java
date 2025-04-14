package edu.schoollibrary.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "TBL_BOOK_INVENTORY")
public class BookInventory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String bookIsbn;
  private int totalAssignedToTheLibrary;
  private int totalIntStock;
}
