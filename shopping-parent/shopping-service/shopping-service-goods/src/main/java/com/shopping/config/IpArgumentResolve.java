package com.shopping.config;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import utils.IPUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xcoder
 * @date 2021/8/27 10:58
 */
public class IpArgumentResolve implements HandlerInterceptor {
    /**
     *请求到达控制层之前执行  返回true，则放行，返回false，则拦截
     *handler:请求的对象，如果是控制层请求，则为HandleMethod对象，可通过该对象获取控制层方法信息
     * **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器");
        StringBuffer requestURL = request.getRequestURL();
        System.out.println("访问路径：" + requestURL.toString());
        //判定handler是否为控制层方法
//        if (handler instanceof HandlerMethod){
//            HandlerMethod handlerMethod= (HandlerMethod) handler;
//            //获取方法名
//            String name=handlerMethod.getMethod().getName();
//            System.out.println("方法名："+name);
//            Class<?> beanType = handlerMethod.getBeanType();
//            System.out.println("控制层类名："+beanType.getName());
//        }
        String ipAddr = IPUtil.getIpAddr(request);
        System.out.println(ipAddr);
        return true;
    }

    /**
     * 控制层方法调用之后，视图解析器解析之前执行
     * modelAndView：控制层方法返回的模型视图对象
     * **/
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println("进入拦截器postHandle方法");
    }

    /**
     * 在视图解析器之后执行，通常做一些清理工作
     * **/
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println("进入afterCompletion方法");
    }
}
