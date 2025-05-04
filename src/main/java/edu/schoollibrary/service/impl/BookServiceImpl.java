package edu.schoollibrary.service.impl;

import edu.schoollibrary.entity.Book;
import edu.schoollibrary.entity.BookInventory;
import edu.schoollibrary.entity.BookTracker;
import edu.schoollibrary.exception.AppException;
import edu.schoollibrary.projection.BookProjection;
import edu.schoollibrary.projection.BookProjectionWithCount;
import edu.schoollibrary.repository.BookInventoryRepository;
import edu.schoollibrary.repository.BookRepository;
import edu.schoollibrary.repository.BookTrackerRepository;
import edu.schoollibrary.request.BookInventoryRequest;
import edu.schoollibrary.request.BookRequest;
import edu.schoollibrary.request.BookSelectionRequest;
import edu.schoollibrary.response.AppResponse;
import edu.schoollibrary.response.BookDetail;
import edu.schoollibrary.service.BookService;
import edu.schoollibrary.service.NumberCodec;
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
  private final NumberCodec numberCodec;

  @Override
  public AppResponse<String> addBook(BookRequest bookRequest) {
    long id = numberCodec.decode(bookRequest.getRequesterId());
    userService.isAdmin(id);

    //Builder Pattern
    Book book = Book.builder()
        .authors(bookRequest.getAuthors())
        .title(bookRequest.getTitle())
        .isbn(bookRequest.getIsbn())
        .review(bookRequest.getReview())
        .year(bookRequest.getYear())
        .createdAt(new Date())
        .pages(bookRequest.getPages())
        .build();

    try{
    book = bookRepository.save(book);
    }catch (Exception e){
      throw new AppException("Error creating book inventory confirm book with ISBN " + book.getIsbn() + " does not already exists", "99");
    }

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
    long id = numberCodec.decode(request.getRequesterId());
    userService.isAdmin(id);

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    AppResponse<String> appResponse = new AppResponse<>();
    if(bookInventoryOptional.isEmpty()){
      appResponse.setData("Book with id " + request.getBookId() + " not found");
      appResponse.setCode("99");
      appResponse.setMessage("Failed");

      throw new AppException(appResponse.getData(), appResponse.getCode());
    }

    if(request.getCountAdded() <= 0){
      appResponse.setData("Count added must be greater than 0");
      appResponse.setCode("99");
      appResponse.setMessage("Failed");

      throw new AppException(appResponse.getData(), appResponse.getCode());
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

  // Decorator  pattern
  @Override
  public AppResponse<List<BookDetail>> fetchAllBooks() {
    List<BookProjectionWithCount> allBooks = bookRepository.fetchAllBook();

    AppResponse<List<BookDetail>> appResponse = new AppResponse<>();

    String message = String.format("Fetched all books successfully");
    log.info(message);
    appResponse.setData(
        allBooks.stream()
            .map(eachBook -> new BookDetail(eachBook))
            .toList()
    );
    appResponse.setCode("00");
    appResponse.setMessage("Success");
    return appResponse;
  }

  @Override
  @Transactional
  public AppResponse<String> studentBorrowBook(BookSelectionRequest request) {
    long id = numberCodec.decode(request.getRequesterId());
    userService.isStudent(id);

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    if(bookInventoryOptional.isEmpty()){
      throw new AppException("Book with id " + request.getBookId() + " not found", "99");
    }

    BookInventory bookInventory = bookInventoryOptional.get();
    int availableBookCount = bookInventory.getTotalInStock();


    Optional<BookTracker> optionalBookTracker =  bookTrackerRepository
        .findByBookIdAndUserIdAndReturned(request.getBookId(), id, "FALSE");

    if(optionalBookTracker.isPresent()){
      throw new AppException("Book with id " + request.getBookId() + " already borrowed by you", "99");
    }
    if(availableBookCount <= 0){
      throw new AppException("Book with id " + request.getBookId() + " is not available at the moment", "99");
    }
    BookTracker bookTracker = BookTracker.builder()
        .bookId(request.getBookId())
        .count(1)
        .userId(id)
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
    long id = numberCodec.decode(request.getRequesterId());
    userService.isStudent(id);

    Optional<BookInventory> bookInventoryOptional = bookInventoryRepository.findUniqueInventoryByBookId(request.getBookId());
    if(bookInventoryOptional.isEmpty()){
      throw new AppException("Book with id " + request.getBookId() + " not found", "99");
    }

    BookInventory bookInventory = bookInventoryOptional.get();
    int availableBookCount = bookInventory.getTotalInStock();

    Optional<BookTracker> optionalBookTracker =  bookTrackerRepository
        .findByBookIdAndUserIdAndReturned(request.getBookId(), id, "FALSE");

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

  @Override
  public AppResponse<List<BookProjection>> getStudentNotReturnedBooks(Long userId) {
    userService.isStudent(userId);
    List<BookProjection> booksNotReturnedByUser = bookTrackerRepository
        .getBooksNotReturnedByUser(userId);

    AppResponse<List<BookProjection>> appResponse = new AppResponse<>();
    appResponse.setData(booksNotReturnedByUser);
    appResponse.setCode("00");
    appResponse.setMessage("Success");
    return appResponse;
  }
}
