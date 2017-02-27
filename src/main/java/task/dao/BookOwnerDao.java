package task.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import task.dao.models.Book;
import task.dao.models.BookOwner;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class BookOwnerDao {

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(BookOwner bookOwner) {
        getSession().save(bookOwner);
    }

    public void delete(BookOwner bookOwner) {
        getSession().delete(bookOwner);
    }

    @SuppressWarnings("unchecked")
    public List<BookOwner> getAll() {
        //noinspection JpaQlInspection
        return getSession().createQuery("from BookOwner").list();
    }

    @SuppressWarnings("unchecked")
    public List<BookOwner> getByBusy() {
        Timestamp timestampNow = new Timestamp(new Date().getTime());
        //noinspection JpaQlInspection
        return (List<BookOwner>) getSession().createQuery(
                "from BookOwner where (data_time < :timestampNow and data_expiration > timestampNow) or returned = false")
                .setParameter("timestampNow", timestampNow)
                .list();
    }

    @SuppressWarnings("unchecked")
    public List<BookOwner> getByFree() {
        //noinspection JpaQlInspection
        return (List<BookOwner>) getSession().createQuery(
                "from BookOwner where returned = true")
                .list();
    }

    public BookOwner getById(long id) {
        return (BookOwner) getSession().load(BookOwner.class, id);
    }

    public void update(BookOwner bookOwner) {
        getSession().update(bookOwner);
    }

    public BookOwner getByBookId(Integer bookId) {
            //noinspection JpaQlInspection
            return (BookOwner) getSession().createQuery(
                    "from BookOwner where book_id = :bookId")
                    .setParameter("bookId", bookId)
                    .uniqueResult();
       }

    public List<Book> getHistoryRange(Integer userId) {
        List<BookOwner> listOwners;
        //noinspection JpaQlInspection,unchecked
        if(userId ==null){
            //noinspection JpaQlInspection,unchecked
            listOwners = (List<BookOwner>) getSession().createQuery(
                    "from BookOwner")
                    .list();
        } else {
            //noinspection JpaQlInspection,unchecked
            listOwners = (List<BookOwner>) getSession().createQuery(
                    "from BookOwner where customer_id = :userId")
                    .setParameter("userId", userId)
                    .list();
        }

        List<Book> history = new ArrayList<>();
        for (BookOwner item: listOwners            ) {
            //noinspection JpaQlInspection,unchecked
            history.add((Book)getSession().createQuery("from Book where book_id = :bookId")
                    .setParameter("bookId", item.getBook_id())
                    .uniqueResult());
        }
            return history;
    }
}

