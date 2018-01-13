package book.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "book_publisher")
public class BookPublisher implements Serializable{
//    private int id;
    private Book book;
    private Publisher publisher;
    private Date publishedDate;

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    @Id
    @ManyToOne
    @JoinColumn(name = "book_id")
    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Id
    @ManyToOne
    @JoinColumn(name = "publisher_id")
    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    @Column(name = "published_date")
    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

//    @Override
//    public String toString() {
//        return "BookPublisher{" +
//                "book=" + book +
//                ", publisher=" + publisher +
//                ", publishedDate=" + publishedDate +
//                '}';
//    }
}