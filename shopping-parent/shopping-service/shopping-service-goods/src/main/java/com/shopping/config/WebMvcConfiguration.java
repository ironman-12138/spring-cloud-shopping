package com.shopping.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * 配置类，配置自定义返回值处理器
 */
@Configuration
public class WebMvcConfiguration {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Autowired
    private ExternalAPIRequestBodyArgumentResolver externalAPIRequestBodyArgumentResolver;
    @Resource
    private RequestJsonHandlerMethodArgumentResolver requestJsonHandlerMethodArgumentResolver;

    @PostConstruct
    public void init() {
//        final List<HandlerMethodReturnValueHandler> newHandlers = new LinkedList<>();
//        final List<HandlerMethodReturnValueHandler> originalHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
//        if (null != originalHandlers) {
//            newHandlers.addAll(originalHandlers);
//            // 获取处理器应处于的位置，需要在RequestResponseBodyMethodProcessor之前
//            final int index = obtainValueHandlerPosition(originalHandlers, RequestResponseBodyMethodProcessor.class);
//            newHandlers.add(index, externalAPIResponseBodyReturnValueHandler);
//        } else {
//            newHandlers.add(externalAPIResponseBodyReturnValueHandler);
//        }
//        requestMappingHandlerAdapter.setReturnValueHandlers(newHandlers);

        List<HandlerMethodArgumentResolver> newResolvers = new LinkedList<>();
        List<HandlerMethodArgumentResolver> originalResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        newResolvers.addAll(originalResolvers);
        newResolvers.add(1,externalAPIRequestBodyArgumentResolver);
        newResolvers.add(2,requestJsonHandlerMethodArgumentResolver);
        requestMappingHandlerAdapter.setArgumentResolvers(newResolvers);
    }

    /**
     * 获取让自定义处理器生效应处于的位置
     *
     * @param originalHandlers 已经添加的返回值处理器
     * @param handlerClass     返回值处理器的类型
     * @return 自定义处理器需要的位置
     */
    private int obtainValueHandlerPosition(final List<HandlerMethodReturnValueHandler> originalHandlers, Class<?> handlerClass) {
        for (int i = 0; i < originalHandlers.size(); i++) {
            final HandlerMethodReturnValueHandler valueHandler = originalHandlers.get(i);
            if (handlerClass.isAssignableFrom(valueHandler.getClass())) {
                return i;
            }
        }
        return -1;
    }

}
