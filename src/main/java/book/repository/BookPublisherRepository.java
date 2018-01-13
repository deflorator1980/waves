package book.repository;

import book.model.Book;
import book.model.BookPublisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookPublisherRepository extends JpaRepository<Book, Integer> {
}
