package demo.tcloud.triblewood.qcbm.api;

import demo.tcloud.triblewood.qcbm.common.OrderInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;

import java.util.List;

public interface OrderService {

    Response<OrderInfoDto> purchaseBook(Long userId, Long isbn);

    Response<List<OrderInfoDto>> queryOrder(Long userId);
}
