package demo.tcloud.triblewood.qcbm.user.persist;

import demo.tcloud.triblewood.qcbm.user.entity.UserInfo;
import demo.tcloud.triblewood.qcbm.user.persist.dao.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;

@Component("userPersistCmpt")
public class UserPersistCmptImpl implements UserPersistCmpt {

    private static final Logger log = LoggerFactory.getLogger(UserPersistCmptImpl.class);

    @Resource
    private UserInfoMapper userInfoMapper;


    @Transactional(readOnly = true)
    public UserInfo getUserById(@NotNull Long userId) {
        log.info("[Normal-Traffic] query user by id = {} from table qcbm.userinfo", userId);
        return userInfoMapper.getById(userId);
    }

    @Transactional(readOnly = true)
    public List<UserInfo> getUserByName(@NotNull String userName) {
        log.info("[Normal-Traffic] query user by name = {} from table qcbm.userinfo", userName);
        return userInfoMapper.getByName(userName);
    }

    @Override
    public boolean checkUserByName(String userName) {
        return userInfoMapper.checkUserByName(userName) != null;
    }

    @Transactional
    public int saveUser(@NotNull UserInfo userInfo) {
        log.info("[Normal-Traffic] insert user (name = {}) into table qcbm.userinfo", userInfo.getName());
        return userInfoMapper.insert(userInfo);
    }

    @Transactional
    public int updateUser(@NotNull UserInfo userInfo) {
        log.info("[Normal-Traffic] update user (id = {}) in table qcbm.userinfo", userInfo.getId());
        return userInfoMapper.updateById(userInfo);
    }

    @Transactional
    public int deleteUser(@NotNull Long id) {
        log.info("[Normal-Traffic] delete user {} from table qcbm.userinfo", id);
        return userInfoMapper.deleteByID(id);
    }
}
