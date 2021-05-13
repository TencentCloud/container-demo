package demo.tcloud.triblewood.qcbm.user.persist;

import demo.tcloud.triblewood.qcbm.user.entity.UserInfo;

import java.util.List;

public interface UserPersistCmpt {

    UserInfo getUserById(Long userId);

    List<UserInfo> getUserByName(String userName);

    boolean checkUserByName(String userName);

    int saveUser(UserInfo userInfo);

    int updateUser(UserInfo userInfo);

    int deleteUser(Long id);
}
