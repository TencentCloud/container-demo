package demo.tcloud.triblewood.qcbm.store.service;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.api.StoreService;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.ResponseCode;
import demo.tcloud.triblewood.qcbm.store.model.Bookinfo;
import demo.tcloud.triblewood.qcbm.store.persist.BookPersistCmpt;
import demo.tcloud.triblewood.qcbm.store.persist.CacheCmpt;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@DubboService(version = "${store.service.version}")
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Resource
    private CacheCmpt cacheCmpt;
    @Resource
    private BookPersistCmpt bookPersistCmpt;


    @Override
    public Response<String> addBook(@NotNull BookInfoDto bookinfoDto) {

        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);

        logger.info("StoreService.addBook(), bookinfoDto = {}", JSON.toJSONString(bookinfoDto));

        try {
            Bookinfo entity = new Bookinfo();
            BeanUtils.copyProperties(bookinfoDto, entity);
            bookPersistCmpt.saveBook(entity);
            resp.setMsg(String.format("【添加图书】%s 成功！", bookinfoDto.getTitle()));

            cacheCmpt.cacheBook(bookinfoDto);
        }
        catch (Exception e) {
            logger.error("failed to add bookinfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }
        return resp;
    }


    @Override
    public Response<BookInfoDto> getBookInfoByIsbn(@NotNull Long isbn) {

        Response<BookInfoDto> resp = new Response<BookInfoDto>(ResponseCode.SUCCESS);

        logger.info("StoreService.getBookInfoByIsbn(), isbn = {}", isbn);

        try {
            BookInfoDto dto = cacheCmpt.getBookFromCash(isbn);
            if (null != dto) {
                return resp.setData(dto);
            }

            Bookinfo entity = bookPersistCmpt.getBook(isbn);
            if (null == entity) {
                logger.warn("不存在 ISBN = " + isbn + " 的图书");
                resp.setFailue("不存在 ISBN = " + isbn + " 的图书");
                return resp;
            }

            dto = new BookInfoDto();
            BeanUtils.copyProperties(entity, dto);
            resp.setData(dto);

            // cash in redis
            cacheCmpt.cacheBook(dto);
        }
        catch (Exception e) {
            logger.error("exception during bookinfoMapper.getByIsbn", e);
            resp.setFailue("DB 异常 " + e.getMessage());
        }
        return resp;
    }

    @Override
    public Response<List<BookInfoDto>> getAllBooks() {

        Response<List<BookInfoDto>> resp = new Response<List<BookInfoDto>>(ResponseCode.SUCCESS);

        logger.info("StoreService.getAllBooks()");
            
        try {
            List<BookInfoDto> dtoList = cacheCmpt.getAllBooksFromCache();
            if (!CollectionUtils.isEmpty(dtoList)) {
                return resp.setData(dtoList);
            }

            List<Bookinfo> entityList = bookPersistCmpt.getAllBooks();
            if (CollectionUtils.isEmpty(entityList)) {
                return resp.setFailue("书库为空！");
            }

            dtoList = new ArrayList<>(entityList.size());
            for (Bookinfo entity : entityList) {

                BookInfoDto dto = new BookInfoDto();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            }
            resp.setData(dtoList);

            // cache in redis
            cacheCmpt.cacheAllBooks(dtoList);
        }
        catch (Exception e) {
            logger.error("exception during bookinfoMapper.getByIsbn", e);
            resp.setFailue("DB 异常 " + e.getMessage());
        }
        return resp;
    }


    @Override
    public Response<String> modifyBookinfo(@NotNull BookInfoDto bookInfoDto) {

        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);

        logger.info("StoreService.modifyBookinfo(), bookinfoDto = {}", JSON.toJSONString(bookInfoDto));

        if (bookInfoDto.getIsbn() == null || bookInfoDto.getIsbn() == 0) {
            String msg = "can't update bookinfo cause both isbn are null. bookinfo = " + JSON.toJSONString(bookInfoDto);
            logger.warn(msg);
            return resp.setFailue(msg);
        }

        try {
            Bookinfo entity = new Bookinfo();
            BeanUtils.copyProperties(bookInfoDto, entity);

            bookPersistCmpt.updateBook(entity);
            resp.setMsg(String.format("【修改图书】%s 成功！", bookInfoDto.getTitle()));
        }
        catch (Exception e) {
            logger.error("failed to modify bookinfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }

    @Override
    public Response<String> removeBookByIsbn(@NotNull Long isbn) {

        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);

        logger.info("StoreService.removeBookByIsbn(), isbn = {}", isbn);

        try {
            bookPersistCmpt.deleteBook(isbn);
            resp.setMsg(String.format("【删除图书】isbn = %d 成功！", isbn));
        }
        catch (Exception e) {
            logger.error("failed to delete bookinfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }
}
