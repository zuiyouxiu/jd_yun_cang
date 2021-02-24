package com.lqh.dev.filters;

import com.lqh.dev.domain.CurrentLoginContext;
import com.lqh.dev.utils.CookieUtil;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractPreAuthFilter extends OncePerRequestFilter {

    protected String filterName;

    protected CookieUtil cookieUtil = new CookieUtil();

    protected void setRequestParams(HttpServletRequest request, CurrentLoginContext loginContext) {
        request.setAttribute("pin", loginContext.getUserPin());
        request.setAttribute(getCookieName(), loginContext.getCookieValue());
    }

    /**
     * 解析cookie
     *
     * @param request
     * @param response
     * @return
     */
    protected abstract CurrentLoginContext parseTicket(HttpServletRequest request, HttpServletResponse response);

    protected abstract String getCookieName();

    @Override
    protected String getAlreadyFilteredAttributeName() {
        String name = super.getAlreadyFilteredAttributeName();
        this.filterName = name;
        return name;
    }
}
