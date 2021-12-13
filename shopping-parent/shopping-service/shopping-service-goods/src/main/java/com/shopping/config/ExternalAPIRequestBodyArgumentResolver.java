package com.shopping.config;

import com.alibaba.fastjson.JSONObject;
import com.shopping.config.annotate.CustomParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Conventions;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;

@Slf4j
@Component
public class ExternalAPIRequestBodyArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(CustomParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        methodParameter = methodParameter.nestedIfOptional();
        Object arg = readArgument(methodParameter, modelAndViewContainer, nativeWebRequest);
        String name = Conventions.getVariableNameForParameter(methodParameter);

        WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, arg, name);
        if (arg != null) {
            validateIfApplicable(binder, methodParameter);
            if (binder.getBindingResult().hasErrors() && isBindExceptionRequired(binder, methodParameter)) {
                throw new MethodArgumentNotValidException(methodParameter, binder.getBindingResult());
            }
        }
        modelAndViewContainer.addAttribute(BindingResult.MODEL_KEY_PREFIX + name, binder.getBindingResult());

        return adaptArgumentIfNecessary(arg, methodParameter);
    }

    private Object readArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest)
            throws IOException, HttpMessageNotReadableException, IllegalAccessException, InstantiationException {
        CustomParam log = parameter.getParameterAnnotation(CustomParam.class);
        System.out.println("ExternalAPIRequestBodyArgumentResolver(56)------->" + parameter.getParameterType());
        System.out.println("ExternalAPIRequestBodyArgumentResolver(57)------->" + mavContainer);
        String uri = webRequest.getNativeRequest(HttpServletRequest.class).getRequestURI();
        System.out.println("ExternalAPIRequestBodyArgumentResolver(59)------->" + uri);
        String value = log.value();
        System.out.println("ExternalAPIRequestBodyArgumentResolver(61)------->" + value);
        Class clazz = parameter.getParameterType();
        String jsonData = webRequest.getParameter(value);
        if (jsonData == null) {
            return clazz.newInstance();
        }
        Object object = JSONObject.parseObject(jsonData, clazz);
        System.out.println("ExternalAPIRequestBodyArgumentResolver（68）-->" + object);
        return object;
    }

    private Object adaptArgumentIfNecessary(Object arg, MethodParameter parameter) {
        if (!parameter.isOptional()) {
            return arg;
        }
        if (arg == null || (arg instanceof Collection && ((Collection<?>) arg).isEmpty())
                || (arg instanceof Object[] && ((Object[]) arg).length == 0)) {
            return Optional.empty();
        }
        return Optional.of(arg);
    }

    private void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
        Annotation[] annotations = parameter.getParameterAnnotations();
        for (Annotation ann : annotations) {
            Validated validatedAnn = AnnotationUtils.getAnnotation(ann, Validated.class);
            if (validatedAnn != null || ann.annotationType().getSimpleName().startsWith("Valid")) {
                Object hints = (validatedAnn != null ? validatedAnn.value() : AnnotationUtils.getValue(ann));
                Object[] validationHints = (hints instanceof Object[] ? (Object[]) hints : new Object[]{hints});
                binder.validate(validationHints);
                break;
            }
        }
    }

    private boolean isBindExceptionRequired(WebDataBinder binder, MethodParameter parameter) {
        int i = parameter.getParameterIndex();
        Class<?>[] paramTypes = parameter.getMethod().getParameterTypes();
        boolean hasBindingResult = (paramTypes.length > (i + 1) && Errors.class.isAssignableFrom(paramTypes[i + 1]));
        return !hasBindingResult;
    }
}
