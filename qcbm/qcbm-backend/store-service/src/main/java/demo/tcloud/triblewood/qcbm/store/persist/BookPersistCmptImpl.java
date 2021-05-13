package demo.tcloud.triblewood.qcbm.store.persist;

import demo.tcloud.triblewood.qcbm.store.model.Bookinfo;
import demo.tcloud.triblewood.qcbm.store.persist.dao.BookinfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("bookPersistCmpt")
public class BookPersistCmptImpl implements BookPersistCmpt {

    private static final Logger log = LoggerFactory.getLogger(BookPersistCmptImpl.class);

    @Resource
    private BookinfoMapper bookinfoMapper;

    @Override
    @Transactional(readOnly = true)
    public Bookinfo getBook(Long isbn) {
        log.info("query book information by isbn = {} from table qcbm.bookinfo", isbn);
        return bookinfoMapper.getByIsbn(isbn);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bookinfo> getAllBooks() {

        log.info("get all books from table qcbm.bookinfo");
        return bookinfoMapper.getAll();
    }

    @Override
    @Transactional
    public int saveBook(Bookinfo bookinfo) {

        log.info("insert book (isbn = {}) into table qcbm.bookinfo", bookinfo.getIsbn());
        return bookinfoMapper.insert(bookinfo);
    }

    @Override
    @Transactional
    public int updateBook(Bookinfo bookinfo) {

        log.info("update book (isbn = {}) in table qcbm.bookinfo", bookinfo.getIsbn());
        return bookinfoMapper.updateById(bookinfo);
    }

    @Override
    @Transactional
    public int deleteBook(Long isbn) {

        log.info("delete book (isbn = {}) from table qcbm.bookinfo", isbn);
        return bookinfoMapper.deleteByIsbn(isbn);
    }
}
