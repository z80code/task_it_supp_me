package task.controllers;

import org.springframework.web.bind.annotation.RestController;
import task.dao.models.Book;
import task.dao.BookDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import task.enums.RequestStatus;
import task.models.view.ViewModel;

import java.util.List;

@RestController
@RequestMapping(value = "/books")
public class BookController {

    @Autowired
    private BookDao _bookDao;

    @RequestMapping(value = "/all")
    @ResponseBody
    public ViewModel getAll(Integer from, Integer size) {
        List<Book> books;
            try {
                books = _bookDao.getAllRange(from, size);
                return new ViewModel(RequestStatus.OK, null, books);
            } catch (Exception ex) {
                return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
                // TODO logging
            }
    }

    @RequestMapping(value = "/size")
    @ResponseBody
    public ViewModel getAll() {
        List<Book> books;
        try {
            books = _bookDao.getAllRange(null, null);
            return new ViewModel(RequestStatus.OK, null, books.size());
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }

    @RequestMapping(value = "/busy")
    @ResponseBody
    public ViewModel getBusy(Integer from, Integer size) {
        List<Book> books;
        try {
            books = _bookDao.getAllBusyRange(from, size);
            return new ViewModel(RequestStatus.OK, null, books);
        } catch (Exception ex) {
            return new ViewModel(RequestStatus.ERROR, ex.getMessage(), null);
            // TODO logging
        }
    }
}
