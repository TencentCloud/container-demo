package demo.tcloud.triblewood.qcbm.order.persist;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.order.model.OrderInfo;
import demo.tcloud.triblewood.qcbm.order.persist.dao.OrderInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Component("orderPersistCmpt")
public class OrderPersistCmptImpl implements OrderPersistCmpt {

    private static final Logger log = LoggerFactory.getLogger(OrderPersistCmptImpl.class);

    @Resource
    private OrderInfoMapper orderInfoMapper;


    @Transactional
    public int saveOrderInfo(OrderInfo orderInfo) {
        log.info("insert orderInfo into table qcbm.order");
        return orderInfoMapper.insert(orderInfo);
    }

    @Transactional(readOnly = true)
    public List<OrderInfo> getOrderByUserId(Long userId) {
        log.info("select user's (userId = {}) orderInfo in table qcbm.order", userId);
        return orderInfoMapper.selectByUserId(userId);
    }
}
