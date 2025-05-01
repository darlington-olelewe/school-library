package edu.schoollibrary.repository;

import edu.schoollibrary.entity.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory,Long> {

  Optional<BookInventory> findUniqueInventoryByBookId(@Param("bookId") Long bookId);
}
