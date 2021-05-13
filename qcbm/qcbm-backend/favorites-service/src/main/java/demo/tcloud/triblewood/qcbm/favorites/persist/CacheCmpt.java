package demo.tcloud.triblewood.qcbm.favorites.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("cashCmpt")
public class CacheCmpt {

    private static final Logger logger = LoggerFactory.getLogger(CacheCmpt.class);

    private static final String USER_FAVORITES_CASH_KEY_PREFIX = "User.Favorites.";

    @Resource
    private RedisTemplate<String, UserInfoDto> redisTemplate;


    public void cashUserFavoriteBook(Long userId, BookInfoDto bookInfoDto) {

        String key = USER_FAVORITES_CASH_KEY_PREFIX + userId;
        logger.info("cash book for user: userId = {}, isbn = {}", userId, bookInfoDto.getIsbn());

        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            operations.putIfAbsent(key, bookInfoDto.getIsbn(), bookInfoDto);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public void delUserFavoriteFromCache(Long userId, Long isbn) {

        String key = USER_FAVORITES_CASH_KEY_PREFIX + userId;
        logger.info("delete user favorite book from cache by key: {}", key);
        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            operations.delete(key, isbn);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public List<BookInfoDto> getUserFavorites(Long userId) {

        String key = USER_FAVORITES_CASH_KEY_PREFIX + userId;
        logger.info("get user favorites from cache with key = {}", key);
        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            List<BookInfoDto> dtoList = operations.values(key);
            if (CollectionUtils.isEmpty(dtoList)) {
                logger.info("redis hasn't user favorites with key = {}", key);
            }

            logger.info("redis respons: key = {}, userInfoDto = {}", key, JSON.toJSONString(dtoList));
            return dtoList;
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }

    public void cashUserFavorites(Long userId, List<BookInfoDto> dtoList) {

        String key = USER_FAVORITES_CASH_KEY_PREFIX + userId;
        logger.info("cash user favorite books with list, userId = {}", userId);

        try {
            Map<Long, BookInfoDto> bookInfoDtoMap = new HashMap<>(dtoList.size());
            dtoList.forEach(book -> {
                bookInfoDtoMap.put(book.getIsbn(), book);
            });

            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            operations.putAll(key, bookInfoDtoMap);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }
}
