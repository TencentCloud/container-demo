package demo.tcloud.triblewood.qcbm.web.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="用户信息")
public class SignInVO {

    @ApiModelProperty(value = "用户 ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "JWT Token")
    private String jwt;
}
