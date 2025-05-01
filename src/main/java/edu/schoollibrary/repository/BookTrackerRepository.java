package edu.schoollibrary.repository;

import edu.schoollibrary.entity.Book;
import edu.schoollibrary.entity.BookTracker;
import edu.schoollibrary.projection.BookProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookTrackerRepository extends JpaRepository<BookTracker,Long> {


  Optional<BookTracker> findByBookIdAndUserIdAndReturned(Long bookId, Long userId, String returned);

  @Query(value="select book.id id, book.title title, book.authors authors, book.review review, book.isbn isbn, book.pages pages, book.year year\n" +
      "from tbl_book book\n" +
      "         join tbl_book_tracker track\n" +
      "              on book.id = track.book_id\n" +
      "where track.user_id =:userId and track.returned ='FALSE'", nativeQuery = true)
  List<BookProjection> getBooksNotReturnedByUser(@Param("userId") Long userId);
}
