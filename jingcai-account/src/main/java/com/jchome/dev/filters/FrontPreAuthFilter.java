package com.lqh.dev.filters;

import com.lqh.dev.domain.CurrentLoginContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class FrontPreAuthFilter extends AbstractUcPreAuthFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("进入 " + super.filterName);
        CurrentLoginContext currentLoginContext = this.parseTicket(request, response);
        if (currentLoginContext == null) {
            throw new RuntimeException("用户未登录");
        }
        setRequestParams(request, currentLoginContext);
        filterChain.doFilter(request, response);
    }
}
