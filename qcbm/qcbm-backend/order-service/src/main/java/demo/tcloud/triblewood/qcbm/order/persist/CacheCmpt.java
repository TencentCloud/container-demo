package demo.tcloud.triblewood.qcbm.order.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.OrderInfoDto;
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

    private static final String ORDER_CASH_PREFIX = "user.order.";

    @Resource
    private RedisTemplate<String, OrderInfoDto> redisTemplate;


    public void cashUserOrder(Long userId, OrderInfoDto orderInfoDto) {

        String key = ORDER_CASH_PREFIX + userId;
        logger.info("cash order for user: userId = {}, orderId = {}", userId, orderInfoDto.getId());

        try {
            HashOperations<String, Long, OrderInfoDto> operations = redisTemplate.opsForHash();
            operations.putIfAbsent(key, orderInfoDto.getId(), orderInfoDto);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }

    public List<OrderInfoDto> getUserOrders(Long userId) {

        String key = ORDER_CASH_PREFIX + userId;
        logger.info("get user orders from cache with key = {}", key);
        try {
            HashOperations<String, Long, OrderInfoDto> operations = redisTemplate.opsForHash();
            List<OrderInfoDto> dtoList = operations.values(key);
            if (CollectionUtils.isEmpty(dtoList)) {
                logger.info("redis hasn't user orders with key = {}", key);
                return null;
            }

            logger.debug("redis respons: key = {}, orderInfoList = {}", key, JSON.toJSONString(dtoList));
            return dtoList;
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
            return null;
        }
    }

    public void cashOrders(Long userId, List<OrderInfoDto> dtoList) {

        String key = ORDER_CASH_PREFIX + userId;
        logger.info("cash user orderList, userId = {}", userId);

        try {
            Map<Long, OrderInfoDto> orderInfoMap = new HashMap<>(dtoList.size());
            dtoList.forEach(order -> {
                orderInfoMap.put(order.getId(), order);
            });

            HashOperations<String, Long, OrderInfoDto> operations = redisTemplate.opsForHash();
            operations.putAll(key, orderInfoMap);
        }
        catch (Exception e) {
            logger.error("redis 异常", e);
        }
    }
}
