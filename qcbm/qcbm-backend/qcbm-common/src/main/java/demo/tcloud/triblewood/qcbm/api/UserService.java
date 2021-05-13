package demo.tcloud.triblewood.qcbm.api;

import demo.tcloud.triblewood.qcbm.common.Response;
import demo.tcloud.triblewood.qcbm.common.UserInfoDto;

import java.util.List;

public interface UserService {

    Response<UserInfoDto> getUserInfoById(Long userId);
    Response<List<UserInfoDto>> getUserInfoByName(String userName);
    Response<String> addUser(UserInfoDto userInfoDto);
    Response<String> modifyUserInfo(UserInfoDto UserInfoDto);
    Response<String> deleteUserById(Long userId);

}