package demo.tcloud.triblewood.qcbm.favorites.service;

import demo.tcloud.triblewood.qcbm.api.FavoriteService;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.ResponseCode;
import demo.tcloud.triblewood.qcbm.favorites.entity.FavoritesInfo;
import demo.tcloud.triblewood.qcbm.favorites.persist.CacheCmpt;
import demo.tcloud.triblewood.qcbm.favorites.persist.FavoritesPersistCmpt;
import demo.tcloud.triblewood.qcbm.favorites.remote.StoreClient;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@DubboService(version = "${favorites.service.version}")
public class FavoriteServiceImpl implements FavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    private static final String FAVORITES_CACHE_KEY_PREFIX = "user.favorites.";

    @Resource
    CacheCmpt cacheCmpt;
    @Resource
    private FavoritesPersistCmpt favoritesPersistCmpt;
    @Resource
    private StoreClient storeClient;


    @Override
    public Response<String> addUserFavoriteBook(Long userId, Long isbn) {
        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);
        logger.info("FavoriteService.addUserFavoriteBook(), userID = {}, isbn={}", userId, isbn);

        if ((null == userId || 0 == userId) || (null == isbn || 0 == isbn)) {
            return resp.setFailue("非法参数！");
        }

        try {
            FavoritesInfo entity = new FavoritesInfo(userId, isbn);
            if (favoritesPersistCmpt.favoriteExist(entity)) {
                logger.info("book isbn = {} 已被用户 userId = {} 收藏", isbn, userId);
                return resp;
            }

            favoritesPersistCmpt.addUserFavorite(entity);
            resp.setMsg("用户 userId = " + userId + " 成功收藏 book isbn = " + isbn);

            BookInfoDto dto = storeClient.getBookInfoByIsbn(isbn);
            cacheCmpt.cashUserFavoriteBook(userId, dto);
        }
        catch (Exception e) {
            logger.error("failed to add FavoritesInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }
        return resp;
    }


    public Response<String> delUserFavoriteBook(Long userId, Long isbn) {
        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);
        logger.info("FavoriteService.delUserFavoriteBook(), userID = {}, isbn={}", userId, isbn);

        if ((null == userId || 0 == userId) || (null == isbn || 0 == isbn)) {
            return resp.setFailue("非法参数！");
        }

        try {
            FavoritesInfo entity = new FavoritesInfo(userId, isbn);
            favoritesPersistCmpt.deleteUserFavorite(entity);

            resp.setMsg("用户 userId = " + userId + " 成功删除收藏 book isbn = " + isbn);

            cacheCmpt.delUserFavoriteFromCache(userId, isbn);
        }
        catch (Exception e) {
            logger.error("failed to delete FavoritesInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }
        return resp;
    }


    public Response<List<BookInfoDto>> queryUserFavorites(Long userId) {
        Response<List<BookInfoDto>> resp = new Response<List<BookInfoDto>>(ResponseCode.SUCCESS);
        logger.info("FavoriteService.queryUserFavorites(), userID = {}", userId);

        if (null == userId || 0 == userId) {
            return resp.setFailue("非法参数，userId 不能为空！");
        }

        try {
            List<BookInfoDto> dtoList = cacheCmpt.getUserFavorites(userId);
            if (null != dtoList) {
                return resp.setData(dtoList);
            }

            List<Long> isbnList = favoritesPersistCmpt.getUserFavoriteBookIsbn(userId);
            if (null == isbnList || isbnList.size() == 0) {
                logger.info("用户 userId = {} 无收藏.", userId);
                return resp;
            }

            List<BookInfoDto> bookInfoDtoList = new ArrayList<>();
            isbnList.forEach(isbn -> {
                BookInfoDto dto = storeClient.getBookInfoByIsbn(isbn);
                if (dto != null) {
                    bookInfoDtoList.add(dto);
                }
            });

            // set in redis
            logger.info("缓存用户信息， UserId={})", userId);
            cacheCmpt.cashUserFavorites(userId, bookInfoDtoList);

            return resp.setData(bookInfoDtoList);
        }
        catch (Exception e) {
            logger.error("failed to delete FavoritesInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }
        return resp;
    }
}
