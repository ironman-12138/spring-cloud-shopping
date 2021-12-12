package com.shopping.config;

import com.alibaba.fastjson.JSONObject;
import com.shopping.inter.RequestJson;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RequestJsonHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestJson.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        RequestJson requestJson = parameter.getParameterAnnotation(RequestJson.class);
        String value = requestJson.value();
        Class clazz = parameter.getParameterType();
        String jsonData = webRequest.getParameter(value);
        System.out.println("24-->" + jsonData);
        if (jsonData == null) {
            return clazz.newInstance();
        }
        Object object = JSONObject.parseObject(jsonData, clazz);
        System.out.println("29-->" + object);
        return object;
    }
}
