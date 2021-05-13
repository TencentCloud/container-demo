package demo.tcloud.triblewood.qcbm.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

    SUCCESS(10000, "成功"),
    FAILURE(10001, "失败");

    private final int code;
    private final String desc;
}

