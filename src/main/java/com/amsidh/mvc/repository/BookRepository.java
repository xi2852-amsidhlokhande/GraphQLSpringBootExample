package com.amsidh.mvc.repository;

import com.amsidh.mvc.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Optional<Book> findByPublisherIgnoreCase(String publisher);
    Optional<List<Book>> findBookByIsnAndTitleAndPublisherAllIgnoringCase(String isn, String title, String publisher);
}
