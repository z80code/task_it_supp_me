package task.dao.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Entity
@Table(name="book_owner")
public class BookOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull
    private long book_id;
    @NotNull
    private long customer_id;
    @NotNull
    private Timestamp date_time;
    @NotNull
    private Long days;
    private boolean returned;

    public BookOwner() {
    }

    public BookOwner(long customer_id) {
        this.customer_id = customer_id;
    }

    public BookOwner(long book_id, long customer_id, Timestamp date_time, Long days, boolean returned) {
        this.book_id = book_id;
        this.customer_id = customer_id;
        this.date_time = date_time;
        this.days = days;
        this.returned = returned;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public Timestamp getDate_time() {
        return date_time;
    }

    public void setDate_time(Timestamp date_time) {
        this.date_time = date_time;
    }

    public Long getDays() {
        return days;
    }

    public void setDays(Long days) {
        this.days = days;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }
}
