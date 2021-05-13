package demo.tcloud.triblewood.qcbm.order.remote;

import demo.tcloud.triblewood.qcbm.api.StoreService;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.ResponseCode;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("storeClient")
public class StoreClient {

    private static final Logger log = LoggerFactory.getLogger(StoreClient.class);

    @DubboReference(version = "1.0.0")
    private StoreService storeService;

    public Response<BookInfoDto> getBookInfoByIsbn(Long isbn) {

        try {
            return storeService.getBookInfoByIsbn(isbn);
        }
        catch (Exception e) {
            log.error("store-service exception when storeService.getBookInfoByIsbn({})", isbn, e);
            return new Response<BookInfoDto>(ResponseCode.FAILURE).setMsg("store-service exception");
        }
    }
}
