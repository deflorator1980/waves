package book.repository;

import book.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

//public interface BookRepository extends JpaRepository<Book, Integer>{
public interface BookRepository extends CrudRepository<Book, Integer> {
    Book findBookByName(String name);
}