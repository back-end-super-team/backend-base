package backend.base.data.api;

import backend.base.enums.ResponseEnum;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
public class ApiResponse implements Serializable {

    private int code;
    private String message;
    private Object payload;

    public ApiResponse(int code, String message, Object object) {
        this.code = code;
        this.message = message;

        if (object != null) {
            if (object instanceof List<?>) {
                this.payload = new ListResponse<List<?>>(((List<?>) object).size(), (List<?>) object);
            }
            else if (object instanceof Page<?>) {
                this.payload = new PageResponse<List<?>>(
                        ((Page<?>) object).getTotalElements(),
                        ((Page<?>) object).getPageable().getPageNumber(),
                        ((Page<?>) object).getTotalPages(),
                        ((Page<?>) object).getPageable().getPageSize(),
                        ((Page<?>) object).getContent()
                );
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
