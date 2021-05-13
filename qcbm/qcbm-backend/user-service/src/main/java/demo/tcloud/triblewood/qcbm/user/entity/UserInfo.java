package demo.tcloud.triblewood.qcbm.user.entity;

import lombok.Data;
import java.util.Date;

@Data
public class UserInfo {

    private Long id;
    private String name;
    private String password;
    private String role;
    private Float price;
    private Boolean valid;
    private Date createTime;
    private Date updateTime;

}
