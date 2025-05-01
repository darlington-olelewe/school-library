package edu.schoollibrary.controller;


import edu.schoollibrary.projection.BookProjection;
import edu.schoollibrary.request.BookInventoryRequest;
import edu.schoollibrary.request.BookRequest;
import edu.schoollibrary.request.BookSelectionRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/book-management")
@RequiredArgsConstructor
public class BookController {
  private final BookService bookService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/add-book")
  public AppResponse<String> addBook(@RequestBody BookRequest bookRequest) {
    return bookService.addBook(bookRequest);
  }

  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/add-inventory")
  public AppResponse<String> increaseInventoryCount(@RequestBody BookInventoryRequest bookInventoryRequest) {
    return bookService.addInventory(bookInventoryRequest);
  }

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/fetch-all")
  public AppResponse<List<BookProjection>> fetchAllBooks(){
    return bookService.fetchAllBooks();
  }


  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/borrow-book")
  public AppResponse<String> borrowBook(@RequestBody BookSelectionRequest bookSelectionRequest) {
    return bookService.studentBorrowBook(bookSelectionRequest);
  }
  @ResponseStatus(HttpStatus.OK)
  @PostMapping("/return-book")
  public AppResponse<String> returnBook(@RequestBody BookSelectionRequest bookSelectionRequest) {
    return bookService.studentReturnBook(bookSelectionRequest);
  }


}
