package demo.tcloud.triblewood.qcbm.web.security.exception;

/**
 * @description: 自定义异常
 * @author: maxiao1
 * @date: 2019/9/13 17:21
 */
public class TokenIsExpiredException extends Exception {


    public TokenIsExpiredException() {
    }

    public TokenIsExpiredException(String message) {
        super(message);
    }

    public TokenIsExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public TokenIsExpiredException(Throwable cause) {
        super(cause);
    }

    public TokenIsExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
