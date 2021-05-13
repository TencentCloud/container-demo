package demo.tcloud.triblewood.qcbm.web.controller;

import demo.tcloud.triblewood.qcbm.api.FavoriteService;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.web.vo.BookInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@Api(description="Admin APIs",tags={"管理接口"})
@RestController
@RequestMapping("/api/favorites")
public class FavoritesController {

    private static final Logger logger = LoggerFactory.getLogger(FavoritesController.class);

    @DubboReference(version = "1.0.0")
    private FavoriteService favoriteService;


    @ApiOperation("查询用户收藏的图书")
    @GetMapping(value = "/query/{userId}")
    public @ResponseBody
    List<BookInfoVO> queryUserFavorites( @PathVariable(value = "userId") Long userId, HttpServletResponse httpResp) {

        logger.info("查询用户 userId = {} 收藏的图书", userId);
        try {

            Response<List<BookInfoDto>> resp = favoriteService.queryUserFavorites(userId);
            if (resp.isSuccess()) {

                List<BookInfoVO> voList = new ArrayList<>(resp.getData().size());
                resp.getData().forEach(dto -> {
                    voList.add(new BookInfoVO(dto));
                });
                return voList;
            }
        }
        catch (Exception e) {
            logger.error("favoriteService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return null;
    }

    @ApiOperation("按姓名查询用户信息")
    @GetMapping(value = "/add")
    public String addUserFavoriteBook(@RequestParam Long userId, @RequestParam Long isbn, HttpServletResponse httpResp) {
        logger.info("add favorites, userId = {} isbn = {}", userId, isbn);

        try {

            Response<String> resp = favoriteService.addUserFavoriteBook(userId, isbn);
            return resp.getMsg();

        } catch (Exception e) {
            logger.error("userService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "userService exception";
        }
    }

    @ApiOperation("删除用户收藏的图书")
    @GetMapping(value = "/delete")
    public String delUserFavoriteBook(@RequestParam Long userId, @RequestParam Long isbn, HttpServletResponse httpResp) {
        logger.info("delete favorites, userId = {} isbn = {}", userId, isbn);

        try {
            Response<String> resp = favoriteService.delUserFavoriteBook(userId, isbn);
            return resp.getMsg();

        } catch (Exception e) {
            logger.error("userService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return "userService exception";
        }
    }
}
