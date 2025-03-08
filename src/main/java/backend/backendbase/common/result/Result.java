package backend.backendbase.common.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class Result<T> implements Serializable {


    private String code;
    private T data;
    private String msg;

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMsg(ResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> judge(boolean status) {
        if (status) {
            return success();
        } else {
            return failed();
        }
    }

    public static <T> Result<T> failed(){
        return result(ResultCode.ERROR_SYSTEM.getCode(),ResultCode.ERROR_SYSTEM.getMessage(),null);
    }

    public static <T> Result<T> failed(String msg){
        return result(ResultCode.ERROR_SYSTEM.getCode(),msg,null);
    }

    public static <T> Result<T> failed(IResultCode resultCode){
        return result(resultCode.getCode(),resultCode.getMessage(),null);
    }

    public static <T> Result<T> failed(IResultCode resultCode,String msg){
        return result(resultCode.getCode(), Objects.nonNull(msg) ? msg : resultCode.getMessage(),null);
    }

    private static <T> Result<T> result(IResultCode resultCode, T data) {
        return result(resultCode.getCode(), resultCode.getMessage(), data);
    }

    private static <T> Result<T> result(String code, String message, T data) {

        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(message);
        result.setData(data);
        return result;
    }


}
