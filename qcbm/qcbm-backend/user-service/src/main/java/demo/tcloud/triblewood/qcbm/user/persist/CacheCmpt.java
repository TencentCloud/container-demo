package demo.tcloud.triblewood.qcbm.user.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component("cashCmpt")
public class CacheCmpt {

    private static final Logger logger = LoggerFactory.getLogger(CacheCmpt.class);

    private static final String USER_ID_CASH_KEY_PREFIX = "user.id.";
    private static final String USER_NAME_CASH_KEY_PREFIX = "user.name.";

    @Resource
    private RedisTemplate<String, UserInfoDto> redisTemplate;


    public List<UserInfoDto> getUserInfoByNameFromCash(String userName) {

        String key = USER_NAME_CASH_KEY_PREFIX + userName;
        logger.info("get user from redis, key = {}", key);

        try {
            ValueOperations<String, UserInfoDto> operations = redisTemplate.opsForValue();
            UserInfoDto dto = operations.get(key);
            if (null == dto) {
                logger.info("redis hasn't value with key = {}", key);
                return null;
            }
            logger.debug("redis respons: key = {}, userInfoDto = {}", key, JSON.toJSONString(dto));
            return Arrays.asList(dto);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }

    public void cacheUserByName(String userName, List<UserInfoDto> dtoList) {
        String key = USER_NAME_CASH_KEY_PREFIX + userName;
        logger.info("cache users with key {}", key);
        try {
            ValueOperations<String, UserInfoDto> operations = redisTemplate.opsForValue();
            operations.set(key, dtoList.get(0), 3, TimeUnit.HOURS);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public void cacheUserById(Long userId, UserInfoDto dto) {
        String key = USER_ID_CASH_KEY_PREFIX + userId;
        logger.info("cache user with key = {}", key);

        try {
            ValueOperations<String, UserInfoDto> options = redisTemplate.opsForValue();
            options.set(key, dto, 3, TimeUnit.HOURS);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public UserInfoDto getUserByIdFromCache(Long userId) {

        String key = USER_ID_CASH_KEY_PREFIX + userId;
        logger.info("get user from redis with key = {}", key);
        try {
            ValueOperations<String, UserInfoDto> options = redisTemplate.opsForValue();
            UserInfoDto dto = options.get(key);
            if (null == dto) {
                logger.info("redis hasn't user info with key = {}", key);
            }

            logger.debug("redis respons: key = {}, userInfoDto = {}", key, JSON.toJSONString(dto));
            return dto;
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }
}
