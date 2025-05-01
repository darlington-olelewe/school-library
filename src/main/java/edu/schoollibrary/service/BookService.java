package edu.schoollibrary.service;

import edu.schoollibrary.projection.BookProjection;
import edu.schoollibrary.projection.BookProjectionWithCount;
import edu.schoollibrary.request.BookInventoryRequest;
import edu.schoollibrary.request.BookRequest;
import edu.schoollibrary.request.BookSelectionRequest;
import edu.schoollibrary.response.AppResponse;

import java.util.List;

public interface BookService {

  AppResponse<String> addBook(BookRequest bookRequest);
  AppResponse<String> addInventory(BookInventoryRequest bookInventoryRequest);
  AppResponse<List<BookProjectionWithCount>> fetchAllBooks();
  AppResponse<String> studentBorrowBook(BookSelectionRequest bookSelectionRequest);
  AppResponse<String> studentReturnBook(BookSelectionRequest bookSelectionRequest);
  AppResponse<List<BookProjection>> getStudentNotReturnedBooks(Long userId);
}
