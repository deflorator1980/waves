package book.controller;

import book.model.BookPublisher;
import book.repository.BookRepository;
import book.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ConfController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @RequestMapping("/books")
    public ResponseEntity<?> showBooks() {
        Set<BookPublisher> bookPublishers = new HashSet<>();
//        schedules.addAll(bookRepository.findOne(1).getSchedules());
        bookPublishers.addAll(bookRepository.findBookByName("BookA").getBookPublishers());

        return new ResponseEntity<Object>(bookPublishers, HttpStatus.OK);
//        return new ResponseEntity<Object>(bookRepository.findAll(), HttpStatus.OK);
    }

    @RequestMapping("/publishers")
    public ResponseEntity<?> showPublishers() {
        return new ResponseEntity<Object>(publisherRepository.findAll(), HttpStatus.OK);
    }

}
