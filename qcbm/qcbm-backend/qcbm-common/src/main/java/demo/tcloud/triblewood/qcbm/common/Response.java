package demo.tcloud.triblewood.qcbm.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class Response<T> implements Serializable {

    private static final long uuid = -1;

    private int code;
    private String msg;
    private T data;


    public Response(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.msg = responseCode.getDesc();
        this.data = null;
    }

    public Response<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public Response<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Response<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Response<T> setFailue(String msg) {
        this.code = ResponseCode.FAILURE.getCode();
        this.msg = msg;
        this.data = null;
        return this;
    }

    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public boolean isFail() {
        return this.code == ResponseCode.FAILURE.getCode();
    }
}
