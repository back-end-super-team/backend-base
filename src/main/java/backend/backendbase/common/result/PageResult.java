package backend.backendbase.common.result;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResult<T> implements Serializable {

    private String code;
    private String msg;
    private Data<T> data;

    public static <T> PageResult<T> success(Page<T> page) {

        PageResult<T> result = new PageResult<T>();
        Data<T> data = new Data<>();

        data.setList(page.getContent());
        data.setTotal(page.getTotalElements());

        result.setCode(ResultCode.SUCCESS.getCode());
        result.setData(data);
        result.setMsg(ResultCode.SUCCESS.getMessage());

        return result;
    }

    @lombok.Data
    public static class Data<T>{
        private List<T> list;
        private long total;
    }
}
