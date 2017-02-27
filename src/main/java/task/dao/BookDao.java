package task.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import task.dao.models.Book;
import task.dao.models.BookOwner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BookDao {

    @SuppressWarnings("SpringAutowiredFieldsWarningInspection")
    @Autowired
    private SessionFactory _sessionFactory;

    private Session getSession() {
        return _sessionFactory.getCurrentSession();
    }

    public void save(Book book) {
        getSession().save(book);
    }

    public void delete(Book book) {
        getSession().delete(book);
    }

    public List<Book> getAll() {
        //noinspection JpaQlInspection,unchecked
        return getSession().createQuery(" from Book").list();
    }

    public Book getByName(String title) {
        //noinspection JpaQlInspection
        return (Book) getSession().createQuery(
                "from Book where title = :title")
                .setParameter("title", title)
                .uniqueResult();
    }

    public Book getById(long id) {
        return (Book) getSession().load(Book.class, id);
    }

    public void update(Book book) {
        getSession().update(book);
    }

    public List<Book> getAllRange(Integer from, Integer size) {
        //noinspection JpaQlInspection
        Query tempList = getSession().createQuery("from Book order by title");
        ranger(tempList, from, size);
        //noinspection unchecked
        return (List<Book>) tempList.list();
    }

    public List<Book> getAllBusyRange(Integer from, Integer size) {
        //noinspection JpaQlInspection
        Query tempList = getSession().createQuery("from Book where book_owner_id!=null");
        ranger(tempList, from, size);
        //noinspection unchecked
        return (List<Book>) tempList.list();
    }

    public List<Book> getAllFreeRange(Integer from, Integer size) {
        //noinspection JpaQlInspection
        Query tempList = getSession().createQuery("from BookOwner where not isnull(book_owner_id)");
        ranger(tempList, from, size);
        //noinspection unchecked
        return (List<Book>) tempList.list();
    }

    public List<Book> getbyIdsRange(Integer from, Integer size, Integer... ids) {
        //noinspection MismatchedQueryAndUpdateOfCollection
        List<Book> result = new ArrayList<>();
        for (Integer id : ids) {
            result.add(getById(id));
        }
        return result;
    }

    private Query ranger(Query tempList, Integer from, Integer size) {
        if (from != null) {
            tempList.setFirstResult(from);
        } else {
            tempList.setFirstResult(0); // default from = 0
        }
        if (size != null) {
            tempList.setMaxResults(size);
        }
        return tempList;
    }
}
