package demo.tcloud.triblewood.qcbm.store.persist;

import demo.tcloud.triblewood.qcbm.store.model.Bookinfo;

import java.util.List;

public interface BookPersistCmpt {

    Bookinfo getBook(Long isbn);
    List<Bookinfo> getAllBooks();
    int saveBook(Bookinfo bookinfo);
    int updateBook(Bookinfo bookinfo);
    int deleteBook(Long isbn);
}
