package com.shopping.config.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import utils.HttpUtil;
import utils.JsonUtil;
import utils.TLocalHelper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoyang
 */
@Order(1)
@Slf4j
@Component
@Aspect
public class AllLogAspect {

    /**
     * 记录接口日志
     *
     * @param proceeding
     * @return
     */
    @Around(value = "execution(* com.shopping..controller..*.*(..))")
    public Object goodsLog(ProceedingJoinPoint proceeding) throws Throwable {
        Long startTime = System.currentTimeMillis();
        // 创建流水号
        TLocalHelper.createSeq();
        Thread.currentThread().setName(TLocalHelper.getSeq());
        HttpServletRequest request = HttpUtil.getRequest();
        if (request != null)
            log.info("Goods接口请求路径:{},请求参数:{},流水号:{}", request.getRequestURI(), JsonUtil.getJson(proceeding.getArgs()), TLocalHelper.getSeq());
        Object result = proceeding.proceed();
        if (request != null)
            log.info("Goods接口执行结束,耗时:{}ms,返回数据:{},流水号:{}", System.currentTimeMillis() - startTime, result.toString(), TLocalHelper.getSeq());
        return result;
    }

}
