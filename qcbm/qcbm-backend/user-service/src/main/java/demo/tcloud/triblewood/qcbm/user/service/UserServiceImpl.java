package demo.tcloud.triblewood.qcbm.user.service;

import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.api.UserService;
import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.ResponseCode;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import demo.tcloud.triblewood.qcbm.user.entity.UserInfo;
import demo.tcloud.triblewood.qcbm.user.persist.CacheCmpt;
import demo.tcloud.triblewood.qcbm.user.persist.UserPersistCmpt;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@DubboService(version = "${user.service.version}")
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private CacheCmpt cacheCmpt;
    @Resource
    private UserPersistCmpt userPersistCmpt;


    public Response<String> addUser(@NotNull UserInfoDto userInfoDto) {

        logger.info("UserService.addUser(), userInfoDto = {}", JSON.toJSONString(userInfoDto));
        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);

        if (StringUtils.isEmpty(userInfoDto.getName())) {
            return resp.setFailue("用户名不能为空！");
        }

        try {

            if (userPersistCmpt.checkUserByName(userInfoDto.getName())) {
                String msg = "用户 " + userInfoDto.getName() + "已存在";
                logger.warn(msg);
                return resp.setFailue(msg);
            }

            UserInfo entity = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, entity);
            entity.setName(userInfoDto.getName());
            entity.setPassword(userInfoDto.getPassword());
            entity.setRole(StringUtils.isEmpty(userInfoDto.getRole()) ? "USER" : userInfoDto.getRole());

            if (userInfoDto.getValid() == null) {
                entity.setValid(true);
            }

            userPersistCmpt.saveUser(entity);
            resp.setMsg(String.format("【添加用户】%s 成功！", userInfoDto.getName()));
        }
        catch (Exception e) {
            logger.error("failed to add UserInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }
        return resp;
    }

    public Response<String> modifyUserInfo(@NotNull UserInfoDto userInfoDto) {

        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);
        logger.info("UserService.modifyUserInfo(), userInfoDto = {}", JSON.toJSONString(userInfoDto));

        if (userInfoDto.getId() == null || userInfoDto.getId() == 0) {
            String msg = "can't update UserInfo cause both isbn are null. UserInfo = " + JSON.toJSONString(userInfoDto);
            logger.warn(msg);
            return resp.setFailue(msg);
        }

        try {
            UserInfo entity = new UserInfo();
            BeanUtils.copyProperties(userInfoDto, entity);
            userPersistCmpt.updateUser(entity);
            resp.setMsg(String.format("【修改用户】%s 成功！", userInfoDto.getName()));
        }
        catch (Exception e) {
            logger.error("failed to modify UserInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }

    public Response<UserInfoDto> getUserInfoById(@NotNull Long userId) {

        Response<UserInfoDto> resp = new Response<UserInfoDto>(ResponseCode.SUCCESS);

        try {
            UserInfoDto dto = cacheCmpt.getUserByIdFromCache(userId);
            if (null != dto) {
                return resp.setData(dto);
            }

            UserInfo entity = userPersistCmpt.getUserById(userId);
            if (entity == null) {
                return resp.setFailue("用户 id = " + userId + " 不存在！");
            }

            dto = new UserInfoDto();
            BeanUtils.copyProperties(entity, dto);
            resp.setData(dto);

            // set in redis
            logger.info("缓存用户信息， UserId={})", userId);
            cacheCmpt.cacheUserById(userId, dto);
        }
        catch (Exception e) {
            logger.error("DB exception", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }

    public Response<List<UserInfoDto>> getUserInfoByName(String userName) {

        Response<List<UserInfoDto>> resp = new Response<List<UserInfoDto>>(ResponseCode.SUCCESS);
        logger.info("UserService.getUserInfoByName(), userName = {}", userName);

        try {
            List<UserInfoDto> dtoList = cacheCmpt.getUserInfoByNameFromCash(userName);
            if (null != dtoList) {
                return resp.setData(dtoList);
            }

            List<UserInfo> entityList = userPersistCmpt.getUserByName(userName);
            if (CollectionUtils.isEmpty(entityList)) {
                return resp.setFailue("用户 " + userName + " 不存在！");
            }

            dtoList = new ArrayList<UserInfoDto>(entityList.size());
            for (UserInfo entity : entityList) {
                UserInfoDto dto = new UserInfoDto();
                BeanUtils.copyProperties(entity, dto);
                dtoList.add(dto);
            }

            resp.setData(dtoList);

            logger.info("缓存用户信息，UserName={})", userName);
            cacheCmpt.cacheUserByName(userName, dtoList);
        }
        catch (Exception e) {
            logger.error("DB exception", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }

    public Response<String> deleteUserById(@NotNull Long userId) {

        Response<String> resp = new Response<String>(ResponseCode.SUCCESS);
        logger.info("UserService.deleteUserById(), userId = {}", userId);

        try {
            userPersistCmpt.deleteUser(userId);
            resp.setMsg(String.format("【删除用户】userId = %d 成功！", userId));
        }
        catch (Exception e) {
            logger.error("failed to delete UserInfo", e);
            resp.setFailue("服务异常，请稍后重试！");
        }

        return resp;
    }
}
