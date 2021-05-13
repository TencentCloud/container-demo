package demo.tcloud.triblewood.qcbm.favorites.remote;

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

    public BookInfoDto getBookInfoByIsbn(Long isbn) {

        try {
            Response<BookInfoDto> resp = storeService.getBookInfoByIsbn(isbn);
            return resp.isFail() ? null : resp.getData();
        }
        catch (Exception e) {
            log.error("store-service exception when storeService.getBookInfoByIsbn({})", isbn, e);
            return null;
        }
    }
}
