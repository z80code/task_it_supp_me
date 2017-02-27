package task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import task.dao.BookDao;
import task.dao.BookOwnerDao;
import task.dao.models.Book;
import task.dao.models.BookOwner;
import task.enums.RequestStatus;
import task.models.view.ViewModel;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/operations")
public class BookOwnerController {

    @Autowired
    private BookOwnerDao _bookOwnerDao;
    @Autowired
    private BookDao _bookDao;

    @RequestMapping(value = "/register")
    @ResponseBody
    public ViewModel regBook(Integer bookId, Integer userId, Timestamp dateTime, long numberDays) {
        BookOwner bookOwner = new BookOwner(bookId, userId,dateTime, numberDays,  false);
        try {
            _bookOwnerDao.save(bookOwner);
            bookOwner =  _bookOwnerDao.getByBookId(bookId);
            Book book = new Book(bookId, bookOwner.getId());
            _bookDao.update(book);
           return new ViewModel(RequestStatus.OK, "Success book registered", null);
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }

    @RequestMapping(value = "/unregister")
    @ResponseBody
    public ViewModel unRegBook(Integer bookId, Integer userId) {
        BookOwner bookOwner = new BookOwner(userId);
        try {
            _bookOwnerDao.delete(bookOwner);
            return new ViewModel(RequestStatus.OK, "Success book unregistered", null);
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }

    @RequestMapping(value = "/history")
    @ResponseBody
    public ViewModel getHistory(Integer userId) {
        try {
            List<Book> bookList = _bookOwnerDao.getHistoryRange(userId);
            return new ViewModel(RequestStatus.OK,null, bookList);
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }

    @RequestMapping(value = "/size")
    @ResponseBody
    public ViewModel getHistory() {
        try {
            List<Book> bookList = _bookOwnerDao.getHistoryRange(null);
            return new ViewModel(RequestStatus.OK,null, bookList.size());
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }
}
