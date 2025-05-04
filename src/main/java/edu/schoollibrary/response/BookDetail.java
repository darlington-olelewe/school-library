package edu.schoollibrary.response;

import edu.schoollibrary.projection.BookProjectionWithCount;
import edu.schoollibrary.service.InventoryDifference;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookDetail implements InventoryDifference {

  private Long id;
  private String title;
  private String authors;
  private String isbn;
  private String review;
  private int year;
  private int pages;
  private int availableBookCount;
  private int totalBookCount;
  private int difference;
  public BookDetail(BookProjectionWithCount bookProjectionWithCount){
    this.id = bookProjectionWithCount.getId();
    this.title = bookProjectionWithCount.getTitle();
    this.authors = bookProjectionWithCount.getAuthors();
    this.isbn = bookProjectionWithCount.getIsbn();
    this.review = bookProjectionWithCount.getReview();
    this.year = bookProjectionWithCount.getYear();
    this.pages = bookProjectionWithCount.getPages();
    this.availableBookCount = bookProjectionWithCount.getAvailableBookCount();
    this.totalBookCount = bookProjectionWithCount.getTotalBookCount();
    this.difference = getBookDifference(totalBookCount, availableBookCount);
  }


  @Override
  public int getBookDifference(int assignedToLibrary, Integer leftInventory) {
    return assignedToLibrary - leftInventory;
  }
}
