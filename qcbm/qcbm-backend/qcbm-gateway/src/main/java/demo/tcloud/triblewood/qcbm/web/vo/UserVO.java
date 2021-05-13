package demo.tcloud.triblewood.qcbm.web.vo;

import demo.tcloud.triblewood.qcbm.common.UserInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {

    private Long id;
    private String userName;
    private String password;
    private String role;


    public UserVO(UserInfoDto dto) {
        this.id = dto.getId();
        this.userName = dto.getName();
        this.password = dto.getPassword();
        this.role = dto.getRole();
    }
}
