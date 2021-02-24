package com.lqh.dev.interceptors;

import com.lqh.dev.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {
    /**
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return false 表示忽略该请求
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        Account account = (Account) session.getAttribute("account");
        log.info("进入拦截器01,执行controller之前调用");
        //如果session里面没有account表示没有登录
        if (account == null) {
            //这个方法返回false表示忽略当前请求，如果一个用户调用了需要登录才能使用接口，如果他没有登录这里会直接忽略掉
            //当然你可以利用response给用户返回一些提示信息，告诉他没登录
            httpServletResponse.getWriter().println("请求被拦截，用户不存在！");
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {
        log.info("执行controller逻辑，在controller的return ModelAndView 之前执行，可以修改ModelAndView值");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
        log.info("controller的return之后，但是在Filter返回给客户端之前执行");

    }
}
