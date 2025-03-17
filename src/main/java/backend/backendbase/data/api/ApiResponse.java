package backend.backendbase.data.api;

import backend.backendbase.enums.ResponseEnum;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ApiResponse implements Serializable {

    private int code;
    private String message;
    private Object payload;

    public ApiResponse(int code, String message, Object object) {
        this.code = code;
        this.message = message;

        if (object != null) {
            Map<String, Object> payload = new HashMap<>();
            if (object instanceof List<?>) {
                payload.put("totalRecords", ((List<?>) object).size());
                payload.put("content", object);
                this.payload = payload;
            }
            else if (object instanceof Page<?>) {
                payload.put("totalRecords", ((Page<?>) object).getTotalElements());
                payload.put("totalPage", ((Page<?>) object).getTotalPages());
                payload.put("page", ((Page<?>) object).getPageable().getPageNumber());
                payload.put("size", ((Page<?>) object).getPageable().getPageSize());
                payload.put("content", ((Page<?>) object).getContent());
                this.payload = payload;
            }
            else {
                this.payload = object;
            }
        }
    }

    public ApiResponse(ResponseEnum status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public ApiResponse(Object object) {
        this(ResponseEnum.SUCCESS, object);
    }

    public ApiResponse(ResponseEnum status, Object object) {
        this(status.getCode(), status.getMessage(), object);
    }

    public static ApiResponse success() {
        return new ApiResponse(ResponseEnum.SUCCESS);
    }

    public static ApiResponse success(Object object) {
        return new ApiResponse(object);
    }

    public static ApiResponse error(ResponseEnum status) {
        return new ApiResponse(status);
    }

    public static ApiResponse error(int code, String message) {
        return new ApiResponse(code, message, null);
    }

    public static ApiResponse error(int code, String message, Object object) {
        return new ApiResponse(code, message, object);
    }

}
