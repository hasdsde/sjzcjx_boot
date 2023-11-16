package cn.sjzcjx.sjzcjx_boot.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author : hasd
 * @version 1.0.0
 * @since : 2023/11/16 15:17
 **/


@AllArgsConstructor
@Data
public class Result {
    private Integer code;
    private String msg;
    private Object obj;


    public static Result OK(Integer c, String m, Object t) {
        return new Result(c, m, t);
    }

    public static Result OKWithData(Object t) {
        return new Result(200, "", t);
    }

    public static Result OKWithMessage(String msg) {
        return new Result(200, msg, null);
    }

    public static Result failWithMessage(Integer code, String m) {
        return new Result(code, m, null);
    }
}
