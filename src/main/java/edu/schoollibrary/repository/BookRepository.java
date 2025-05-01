package edu.schoollibrary.repository;

import edu.schoollibrary.entity.Book;
import edu.schoollibrary.projection.BookProjectionWithCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

  @Query(value="" +
      "select book.id id, book.title title, book.authors authors, book.review review, book.isbn isbn, book.pages pages, " +
      "book.year year, inv.total_in_stock availableBookCount, inv.total_assigned_to_the_library totalBookCount " +
      "from tbl_book book " +
      "join tbl_book_inventory inv " +
      "on book.id = inv.book_id", nativeQuery = true)
  List<BookProjectionWithCount> fetchAllBook();
}
