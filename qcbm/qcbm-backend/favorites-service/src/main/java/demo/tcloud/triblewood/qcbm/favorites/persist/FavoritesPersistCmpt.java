package demo.tcloud.triblewood.qcbm.favorites.persist;

import demo.tcloud.triblewood.qcbm.favorites.entity.FavoritesInfo;
import java.util.List;


public interface FavoritesPersistCmpt {

    List<Long> getUserFavoriteBookIsbn(Long userId);

    boolean favoriteExist(FavoritesInfo favoritesInfo);

    int addUserFavorite(FavoritesInfo favoritesInfo);

    int deleteUserFavorite(FavoritesInfo favoritesInfo);
}
