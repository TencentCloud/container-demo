package demo.tcloud.triblewood.qcbm.store.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
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
    private static final String ALL_BOOKS_CACHE_KEY = "all-books";


    @Resource
    private RedisTemplate<String, BookInfoDto> redisTemplate;


    public BookInfoDto getBookFromCash(Long isbn) {

        logger.info("get book from redis isbn = {}", isbn);

        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            BookInfoDto dto = operations.get(ALL_BOOKS_CACHE_KEY, isbn);
            if (null == dto) {
                logger.info("no book fund in redis isbn = {}", isbn);
            }
            logger.debug("redis respons: bookInfoDto = {}", JSON.toJSONString(dto));
            return dto;
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }

    public void cacheBook(BookInfoDto dto) {
        logger.info("cache book isbn = {}", dto.getIsbn());
        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            operations.putIfAbsent(ALL_BOOKS_CACHE_KEY, dto.getIsbn(), dto);

        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public List<BookInfoDto> getAllBooksFromCache() {

        logger.info("get all books from redis.");
        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();
            List<BookInfoDto> dtoList = operations.values(ALL_BOOKS_CACHE_KEY);
            if (CollectionUtils.isEmpty(dtoList)) {
                logger.info("redis hasn't any book.");
                return null;
            }

            return dtoList;
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }

    public void cacheAllBooks(List<BookInfoDto> dtoList) {
        logger.info("cache all books");

        try {
            HashOperations<String, Long, BookInfoDto> operations = redisTemplate.opsForHash();

            Map<Long, BookInfoDto> bookInfoDtoMap = new HashMap<>(dtoList.size());
            dtoList.forEach(book -> {
                bookInfoDtoMap.put(book.getIsbn(), book);
            });

            operations.putAll(ALL_BOOKS_CACHE_KEY, bookInfoDtoMap);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }
}
