package demo.tcloud.triblewood.qcbm.web.controller;

import demo.tcloud.triblewood.qcbm.api.StoreService;
import demo.tcloud.triblewood.qcbm.api.UserService;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import demo.tcloud.triblewood.qcbm.web.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.dubbo.config.annotation.DubboReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(description="Admin APIs",tags={"管理接口"})
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @DubboReference(version = "1.0.0")
    private UserService userService;
    @DubboReference(version = "1.0.0")
    private StoreService storeService;


//    @ApiOperation("添加图书")
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping(value = "/book/addBook")
//    public @ResponseBody
//    Response<String> addBook(@ApiParam(name="bookInfoDto",value="Json 格式的图书信息",required=true) @RequestBody BookInfoDto bookInfoDto, HttpServletResponse httpResp) {
//
//        return storeService.addBook(bookInfoDto);
//    }
//
//    @ApiOperation("删除图书")
//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping(value = "/book/delete")
//    public @ResponseBody Response<String> deleteBook(@ApiParam(name="isbn",value="国际标准书号ISBN，长整型",required=true) @RequestParam Long isbn, HttpServletResponse httpResp) {
//
//        return storeService.removeBookByIsbn(isbn);
//    }
//
//    @ApiOperation("修改图书信息")
//    @PreAuthorize("hasRole('ADMIN')")
//    @PostMapping(value = "/book/modify")
//    public @ResponseBody Response<String> modifyBookInfo(@ApiParam(name="bookInfoDto",value="Json 格式的图书信息",required=true) @RequestBody BookInfoDto bookInfoDto, HttpServletResponse httpResp) {
//
//        return storeService.modifyBookinfo(bookInfoDto);
//    }

    @ApiOperation("按 userId 查询用户信息")
    @GetMapping(value = "/user/{userId}")
    public @ResponseBody
    UserVO getuserInfo(@ApiParam(name="userId",value="用户 ID，长整型",required=true) @PathVariable(value = "userId") Long userId, HttpServletResponse httpResp) {

        try {
            Response<UserInfoDto> resp = userService.getUserInfoById(userId);
            if (resp.isSuccess()) {
                return new UserVO(resp.getData());
            }

            httpResp.setStatus(HttpStatus.NOT_FOUND.value());

        } catch (Exception e) {
            logger.error("userService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return null;
    }

    @ApiOperation("按姓名查询用户信息")
    @PostMapping(value = "/user/query")
    public @ResponseBody UserVO getUserByName(@RequestParam String userName, HttpServletResponse httpResp) {

        try {
            Response<List<UserInfoDto>> resp = userService.getUserInfoByName(userName);
            if (resp.isSuccess() && !CollectionUtils.isEmpty(resp.getData())) {
                return new UserVO(resp.getData().get(0));
            }

            httpResp.setStatus(HttpStatus.NOT_FOUND.value());

        } catch (Exception e) {
            logger.error("userService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return null;
    }

    @ApiOperation("删除用户")
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/user/delete")
    public String removeteuser(@ApiParam(name="userId",value="用户 ID，长整型",required=true) @RequestParam Long userId, HttpServletResponse httpResp) {

        try {
            Response<String> resp = userService.deleteUserById(userId);
            if (resp.isSuccess()) {
                httpResp.setStatus(HttpStatus.NOT_FOUND.value());
                return "删除成功！";
            }

        } catch (Exception e) {
            logger.error("userService exception", e);
            httpResp.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return "删除失败！";
    }
}
