package com.lqh.dev.agent.hessian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.caucho.HessianServiceExporter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomHessianServiceExporter extends HessianServiceExporter {

    private static final Logger logger = LoggerFactory.getLogger(CustomHessianServiceExporter.CONTENT_TYPE_HESSIAN);

    private String token;

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String value = request.getHeader("token");
        if (value == null || !value.equals(token)) {
            if (logger.isInfoEnabled()) {
                logger.info("token error,value is {}", value);
            }
            return;
        }
        super.handleRequest(request, response);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
