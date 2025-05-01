package edu.schoollibrary.projection;

public interface BookProjection {
  Long getId();
  String getTitle();
  String getAuthors();
  String getIsbn();
  String getReview();
  int getYear();
  int getPages();
  int getAvailableBookCount();
  int getTotalBookCount();
}
