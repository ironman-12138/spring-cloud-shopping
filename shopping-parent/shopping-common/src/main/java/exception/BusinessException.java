package exception;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static java.lang.System.currentTimeMillis;

@ApiModel("异常返回类")
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "code码")
    private String code;

    @ApiModelProperty(value = "当前时间戳")
    private final long timestamp = currentTimeMillis();

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
