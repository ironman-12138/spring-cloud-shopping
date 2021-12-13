package exception.handle;

import exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import utils.Result;
import utils.ResultCode;

import javax.servlet.ServletException;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 系统异常
     */
    @ExceptionHandler(Throwable.class)
    public Result<String> handleException(Throwable e) throws Throwable {
        // 如果异常为ServletException, 直接抛出, 不做处理. 只处理业务异常返回系统错误
        if (e instanceof ServletException) {
            throw e;
        } else if (e instanceof HttpMessageNotReadableException) {
            return Result.errorToClient(ResultCode.PARAM_ERROR.getErrCode(), ResultCode.PARAM_ERROR.getErrMsg());
        } else {
            // 打印堆栈轨迹
            log.error("系统错误：system error:{}", e.getMessage());
            e.printStackTrace();
            return Result.errorToClient(ResultCode.SYSTEM_ERROR.getErrCode(), ResultCode.SYSTEM_ERROR.getErrMsg());
        }
    }


    /**
     * 处理接口参数异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result<String> handleMethodArgumentNotValidException(Exception e) {
        try {
            String errorMsg;
            if (e instanceof MethodArgumentNotValidException) {
                errorMsg = requireNonNull(((MethodArgumentNotValidException) e).getBindingResult().getFieldError()).getDefaultMessage();
            } else {
                errorMsg = requireNonNull(((BindException) e).getBindingResult().getFieldError()).getDefaultMessage();
            }

            log.error("参数异常:{}", errorMsg);
            return Result.errorToClient(ResultCode.PARAM_ERROR.getErrCode(), ResultCode.PARAM_ERROR.getErrMsg());
        } catch (Exception ex) {
            log.error("系统异常：system error:{}", e.toString());
            e.printStackTrace();
            return Result.errorToClient(ResultCode.SYSTEM_ERROR.getErrCode(), ResultCode.SYSTEM_ERROR.getErrMsg());
        }
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e) {
        e.printStackTrace();
        if (isNotBlank(e.getCode())) {
            log.info("业务异常,code:{},message:{}", e.getCode(), e.getMessage());
            return Result.errorToClient(ResultCode.BUS_ERROR.getErrCode(), ResultCode.BUS_ERROR.getErrMsg());
        }
        if (isNotBlank(e.getMessage())) {
            log.info("业务异常:{}", e.getMessage());
            return Result.errorToClient(ResultCode.BUS_ERROR.getErrCode(), e.getMessage());
        } else {
            log.error("系统异常：system error:{}", e.toString());
            return Result.errorToClient(ResultCode.SYSTEM_ERROR.getErrCode(), ResultCode.SYSTEM_ERROR.getErrMsg());
        }
    }
}
