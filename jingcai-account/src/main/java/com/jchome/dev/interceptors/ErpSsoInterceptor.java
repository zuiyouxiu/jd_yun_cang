package com.lqh.dev.interceptors;

import com.jd.common.springmvc.interceptor.SsoInterceptor;
import com.jd.common.web.LoginContext;
import com.jd.ssa.exception.SsoException;
import com.jd.ssa.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liqihua5
 */
@Slf4j
@Component
public class ErpSsoInterceptor extends SsoInterceptor {

    @Resource
    @Override
    public void setSsoService(SsoService ssoService) {
        super.setSsoService(ssoService);
    }

    @Override
    public LoginContext getLoginContext(HttpServletRequest request, HttpServletResponse response) throws SsoException {
        return super.getLoginContext(request, response);
    }
}
