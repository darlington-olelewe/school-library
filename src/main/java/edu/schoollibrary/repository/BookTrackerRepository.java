package edu.schoollibrary.repository;

import edu.schoollibrary.entity.BookTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookTrackerRepository extends JpaRepository<BookTracker,Long> {

//  @Query("select b from BookTracker b where b.bookId = :bookId and b.userId = :userId and b.returned = :returned")
//  Optional<BookTracker> findByBookIdAndUserIdAndReturned(@Param("bookId") Long bookId,
//                                                         @Param("userId") Long userId,
//                                                         @Param("returned") boolean returned);
  Optional<BookTracker> findByBookIdAndUserIdAndReturned(Long bookId, Long userId, String returned);
}
