package edu.schoollibrary.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TBL_BOOK_INVENTORY")
public class BookInventory extends Base{

  @Column(nullable = false, unique = true)
  private long bookId;
  private int totalAssignedToTheLibrary;
  private int totalInStock;
}
