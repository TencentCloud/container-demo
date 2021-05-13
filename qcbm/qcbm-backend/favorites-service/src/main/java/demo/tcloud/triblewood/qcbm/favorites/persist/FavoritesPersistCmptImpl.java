package demo.tcloud.triblewood.qcbm.favorites.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.favorites.entity.FavoritesInfo;
import demo.tcloud.triblewood.qcbm.favorites.persist.dao.FavoritesInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("userPersistCmpt")
public class FavoritesPersistCmptImpl implements FavoritesPersistCmpt {

    private static final Logger log = LoggerFactory.getLogger(FavoritesPersistCmptImpl.class);

    @Resource
    private FavoritesInfoMapper favoritesInfoMapper;


    @Transactional(readOnly = true)
    public List<Long> getUserFavoriteBookIsbn(Long userId) {
        log.info("query user favorite book isbn by id = {} from table qcbm.favorites", userId);
        return favoritesInfoMapper.getUserFavoriteBooks(userId);
    }

    @Transactional
    public int addUserFavorite(FavoritesInfo favoritesInfo) {
        log.info("add user favorite {} into table qcbm.favorites", JSON.toJSONString(favoritesInfo));
        return favoritesInfoMapper.insert(favoritesInfo);
    }

    @Transactional
    public int deleteUserFavorite(FavoritesInfo favoritesInfo) {
        log.info("delete user favorite {} from table qcbm.favorites", JSON.toJSONString(favoritesInfo));
        return favoritesInfoMapper.deleteFavorite(favoritesInfo);
    }

    @Transactional(readOnly = true)
    public boolean favoriteExist(FavoritesInfo favoritesInfo) {
        return favoritesInfoMapper.checkFavorite(favoritesInfo) > 0;
    }
}
