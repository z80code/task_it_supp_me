package task.dao.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private String title;
    @NotNull
    private String author;
    @NotNull
    private int year;
    private Long book_owner_id;

    public Book() {
    }

    public Book(long id) {
        this.id= id;
    }

    public Book(String title, String author, int year, Long book_owner_id) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.book_owner_id = book_owner_id;
    }

    public Book(long id, Long book_owner_id) {
        this.id = id;
        this.book_owner_id = book_owner_id;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Long getBook_owner_id() {
        return book_owner_id;
    }

    public void setBook_owner_id(Long book_owner_id) {
        this.book_owner_id = book_owner_id;
    }
}
