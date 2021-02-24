package com.lqh.dev.filters;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Filter02 implements Filter {

    private String filterName = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterName = filterConfig.getFilterName();
        System.out.println(filterName + " 初始化中");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        System.out.println("进入 " + filterName);
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        request.setAttribute("name", "jack");
        response.setContentType("text/html;charset=UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(filterName + " 销毁中");
    }
}
