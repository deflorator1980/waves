package book;

import book.model.Book;
import book.model.BookPublisher;
import book.model.Publisher;
import book.repository.BookRepository;
import book.repository.PublisherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.util.Date;

@SpringBootApplication
public class HelloJpaApplication implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(HelloJpaApplication.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;


    public static void main(String[] args) {
        SpringApplication.run(HelloJpaApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... strings) throws Exception {
        Book bookA = new Book("BookA");

        Publisher publisherA = new Publisher("Presentation A");

        BookPublisher bookPublisher = new BookPublisher();
        bookPublisher.setBook(bookA);
        bookPublisher.setPublisher(publisherA);
        bookPublisher.setPublishedDate(new Date());
        bookA.getBookPublishers().add(bookPublisher);

        publisherRepository.save(publisherA);
        bookRepository.save(bookA);

        // test
        System.out.println(bookA.getBookPublishers().size());

        // update
//        bookA.getSchedules().remove(bookPublisher);
//        bookRepository.save(bookA);

        // test
        System.out.println(bookA.getBookPublishers().size());
    }
}
