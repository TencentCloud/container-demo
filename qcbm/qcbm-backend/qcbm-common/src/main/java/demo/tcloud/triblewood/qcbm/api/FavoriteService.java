package demo.tcloud.triblewood.qcbm.api;

import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;

import java.util.List;

public interface FavoriteService {

    Response<String> addUserFavoriteBook(Long userId, Long isbn);
    Response<String> delUserFavoriteBook(Long userId, Long isbn);
    Response<List<BookInfoDto>> queryUserFavorites(Long userId);
}
