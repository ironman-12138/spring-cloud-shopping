package com.shopping.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Order(1)
@Slf4j
@Component
@Aspect
public class LogAspect {


    /**
     * 用于记录请求时间
     */
    ThreadLocal<Long> start = new ThreadLocal<>();

    
    private String getLogUserName() {

    	return "未登录";
    }
    /**
     * 记录接口日志
     *
     * @param proceeding
     * @return
     */
    @SuppressWarnings("rawtypes")
    @Around(value = "execution(* com.shopping..controller..*.*(..))")
    public Object log(ProceedingJoinPoint proceeding) throws Throwable {
        start.set(System.currentTimeMillis());
        // 创建流水号
        TLocalHelper.createSeq();
        Thread.currentThread().setName(TLocalHelper.getSeq());
        HttpServletRequest request = HttpUtil.getRequest();
        
        log.info("请求路径:{},请求参数:{},流水号:{}", request.getRequestURI(), JsonUtil.getJson(proceeding.getArgs()), TLocalHelper.getSeq());
        // 获取用户名
        String username = getLogUserName();
        Object result = proceeding.proceed();
        // 如果返回的结果是RestResponse, 并且code值不是00(success), 则记录为异常日志
        if (result instanceof Result) {
            Result res = (Result) result;
            if (!ResultCode.SUCCESS.getErrCode().equals(res.getCode())) {
                log.info("方法请求失败,返回code值:{},error:{}", res.getCode(), res.getMsg());
            } else {
                log.info("方法请求成功,返回code值:{},success:{}", res.getCode(), res.getMsg());
            }
            // 记录日志
            addLog(request, proceeding, res.getCode(), res.getMsg(), username);
        } else {
            // 其他情况记录正常日志
            addLog(request, proceeding, ResultCode.ERROR.getErrCode(), ResultCode.ERROR.getErrMsg(), username);
        }
        log.info("请求结束,耗时:{}ms", System.currentTimeMillis() - start.get());

        return result;
    }

    /**
     * 新增日志
     *
     * @param request
     * @param joinPoint
     * @param status
     * @param errorMsg
     */
    private void addLog(HttpServletRequest request, JoinPoint joinPoint, String status, String errorMsg, String username) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        String methodname = "";
        // 获取Log注解中的操作内容
        ApiOperation apilog = method.getAnnotation(ApiOperation.class);
        if(apilog != null && apilog.value() != null && apilog.value().length() > 0) {
        	methodname = apilog.value();
        }
        log.info("新增日志---->接口名称：{}，状态值：{}，状态信息：{}，调用者：{}", methodname, status, errorMsg, username);

    }

}
