package edu.schoollibrary.service.impl;

import edu.schoollibrary.entity.Book;
import edu.schoollibrary.entity.BookInventory;
import edu.schoollibrary.entity.BookTracker;
import edu.schoollibrary.exception.AppException;
import edu.schoollibrary.projection.BookProjection;
import edu.schoollibrary.repository.AppUserRepository;
import edu.schoollibrary.repository.BookInventoryRepository;
import edu.schoollibrary.repository.BookRepository;
import edu.schoollibrary.repository.BookTrackerRepository;
import edu.schoollibrary.request.BookInventoryRequest;
import edu.schoollibrary.request.BookRequest;
import edu.schoollibrary.request.BookSelectionRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.service.BookService;
import edu.schoollibrary.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {
  private final UserService userService;
  private final BookRepository bookRepository;
  private final BookInventoryRepository bookInventoryRepository;
  private final BookTrackerRepository bookTrackerRepository;

  @Override
  public AppResponse<String> addBook(BookRequest bookRequest) {
    userService.isAdmin(bookRequest.getRequesterId());

    Book book = Book.builder()
        .authors(bookRequest.getAuthors())
        .title(bookRequest.getTitle())
        .isbn(bookRequest.getIsbn())
        .review(bookRequest.getReview())
        .year(bookRequest.getYear())
        .createdAt(new Date())
        .pages(bookRequest.getPages())
        .build();

    book = bookRepository.save(book);
    String message = String.format("Book with id %d added successfully", book.getId());

    log.info(message);

    BookInventory bookInventory = BookInventory.builder()
        .bookId(book.getId())
        .totalInStock(0)
        .totalAssignedToTheLibrary(0)
        .build();

    bookInventoryRepository.save(bookInventory);

    AppResponse<String> appResponse = new AppResponse<>();
    appResponse.setData(message);
    appResponse.setCode("00");
    appResponse.setMessage("Success");

    return appResponse;
  }

  @Override
  public AppResponse<String> addInventory(BookInventoryRequest request) {
    userService.isAdmin(request.getRequesterId());

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    AppResponse<String> appResponse = new AppResponse<>();
    if(bookInventoryOptional.isEmpty()){
      appResponse.setData("Book with id " + request.getBookId() + " not found");
      appResponse.setCode("99");
      appResponse.setMessage("Failed");
      return appResponse;
    }

    BookInventory bookInventory = bookInventoryOptional.get();
    int totalInStock = bookInventory.getTotalInStock() + request.getCountAdded();
    int totalAssignedToTheLibrary = bookInventory.getTotalAssignedToTheLibrary() + request.getCountAdded();

    bookInventory.setTotalInStock(totalInStock);
    bookInventory.setTotalAssignedToTheLibrary(totalAssignedToTheLibrary);

    bookInventoryRepository.save(bookInventory);

    String message = String.format("Book Inventory with book id %d updated successfully", bookInventory.getId());
    log.info(message);

    appResponse.setData(message);
    appResponse.setCode("00");
    appResponse.setMessage("Success");


    return appResponse;
  }

  @Override
  public AppResponse<List<BookProjection>> fetchAllBooks() {
    List<BookProjection> allBooks = bookRepository.fetchAllBook();
    AppResponse<List<BookProjection>> appResponse = new AppResponse<>();

    String message = String.format("Fetched all books successfully");
    log.info(message);
    appResponse.setData(allBooks);
    appResponse.setCode("00");
    appResponse.setMessage("Success");
    return appResponse;
  }

  @Override
  @Transactional
  public AppResponse<String> studentBorrowBook(BookSelectionRequest request) {
    userService.isStudent(request.getRequesterId());

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    if(bookInventoryOptional.isEmpty()){
      throw new AppException("Book with id " + request.getBookId() + " not found", "99");
    }

    BookInventory bookInventory = bookInventoryOptional.get();
    int availableBookCount = bookInventory.getTotalInStock();


    Optional<BookTracker> optionalBookTracker =  bookTrackerRepository
        .findByBookIdAndUserIdAndReturned(request.getBookId(), request.getRequesterId(), "FALSE");

    if(optionalBookTracker.isPresent()){
      throw new AppException("Book with id " + request.getBookId() + " already borrowed by you", "99");
    }
    if(availableBookCount <= 0){
      throw new AppException("Book with id " + request.getBookId() + " is not available at the moment", "99");
    }
    BookTracker bookTracker = BookTracker.builder()
        .bookId(request.getBookId())
        .count(1)
        .pickUpDate(LocalDateTime.now())
        .returned("FALSE")
        .build();

    bookTrackerRepository.save(bookTracker);
    bookInventory.setTotalInStock(availableBookCount - 1);
    bookInventoryRepository.save(bookInventory);


    String message = "Book Successfully borrowed";
    log.info(message);
    AppResponse<String> appResponse = new AppResponse<>();
    appResponse.setData(message);
    appResponse.setCode("00");
    appResponse.setMessage("Success");

    return appResponse;
  }

  @Override
  public AppResponse<String> studentReturnBook(BookSelectionRequest request) {
    userService.isStudent(request.getRequesterId());

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    if(bookInventoryOptional.isEmpty()){
      throw new AppException("Book with id " + request.getBookId() + " not found", "99");
    }

    BookInventory bookInventory = bookInventoryOptional.get();
    int availableBookCount = bookInventory.getTotalInStock();

    Optional<BookTracker> optionalBookTracker =  bookTrackerRepository
        .findByBookIdAndUserIdAndReturned(request.getBookId(), request.getRequesterId(), "FALSE");

    if(optionalBookTracker.isEmpty()){
      throw new AppException("Book with id " + request.getBookId() + " was not borrowed by you", "99");
    }


    BookTracker bookTracker = optionalBookTracker.get();
    bookTracker.setReturned("TRUE");
    bookTracker.setReturnDate(LocalDateTime.now());
    bookTrackerRepository.save(bookTracker);

    bookInventory.setTotalInStock(availableBookCount + 1);
    bookInventoryRepository.save(bookInventory);


    String message = "Book Successfully Returned";
    log.info(message);

    AppResponse<String> appResponse = new AppResponse<>();
    appResponse.setData(message);
    appResponse.setCode("00");
    appResponse.setMessage("Success");

    return appResponse;
  }
}
