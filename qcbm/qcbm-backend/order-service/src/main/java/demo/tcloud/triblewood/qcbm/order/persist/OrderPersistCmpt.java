package demo.tcloud.triblewood.qcbm.order.persist;

import demo.tcloud.triblewood.qcbm.order.model.OrderInfo;

import java.util.List;

public interface OrderPersistCmpt {

    int saveOrderInfo(OrderInfo orderInfo);
    List<OrderInfo> getOrderByUserId(Long userId);
}
