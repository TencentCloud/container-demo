package demo.tcloud.triblewood.qcbm.api;

import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;

import java.util.List;

public interface StoreService {

    Response<BookInfoDto> getBookInfoByIsbn(Long isbn);
    Response<List<BookInfoDto>> getAllBooks();
    Response<String> addBook(BookInfoDto bookInfoDto);
    Response<String> modifyBookinfo(BookInfoDto bookInfoDto);
    Response<String> removeBookByIsbn(Long isbn);
}