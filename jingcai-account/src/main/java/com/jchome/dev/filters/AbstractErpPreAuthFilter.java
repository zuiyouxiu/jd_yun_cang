package com.lqh.dev.filters;

import com.jd.common.web.LoginContext;
import com.lqh.dev.domain.CurrentLoginContext;
import com.lqh.dev.interceptors.ErpSsoInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractErpPreAuthFilter extends AbstractPreAuthFilter {

    private static final String ERP_AUTH_COOKIE_NAME = "sso.jd.com";
    private static final String LOGIN_SOURCE_ERP = "erp";

    @Resource
    private ErpSsoInterceptor erpSsoInterceptor;

    @Override
    protected CurrentLoginContext parseTicket(HttpServletRequest request, HttpServletResponse response) {
        CurrentLoginContext.remove();
        String cookieValue = this.cookieUtil.getCookieValue(request, ERP_AUTH_COOKIE_NAME);
        LoginContext context = null;
        try {
            context = erpSsoInterceptor.getLoginContext(request, response);
        } catch (Exception e) {
            if (logger.isDebugEnabled()) {
                logger.debug("decrypt erp cookie error!", e);
            }
        }
        if (context != null) {
            CurrentLoginContext loginContext = new CurrentLoginContext(context.getPin(), context.getExpiresDate());
            loginContext.setSource(LOGIN_SOURCE_ERP);
            loginContext.setCookieValue(cookieValue);
            return loginContext;
        } else {
            CurrentLoginContext.setTicket(null);
            if (logger.isDebugEnabled()) {
                logger.debug("erp ticket error or ticket expired!");
            }
        }
        return null;
    }

    @Override
    protected String getCookieName() {
        return ERP_AUTH_COOKIE_NAME;
    }
}
