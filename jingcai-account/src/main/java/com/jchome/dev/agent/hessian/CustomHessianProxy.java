package com.lqh.dev.agent.hessian;

import com.caucho.hessian.client.HessianConnection;
import com.caucho.hessian.client.HessianProxy;
import com.caucho.hessian.client.HessianProxyFactory;

import java.net.URL;

public class CustomHessianProxy extends HessianProxy {

    private String token;

    protected CustomHessianProxy(URL url, HessianProxyFactory factory) {
        super(url, factory);
    }

    protected CustomHessianProxy(URL url, HessianProxyFactory factory, Class<?> type, String token) {
        super(url, factory, type);
        this.token = token;
    }

    @Override
    protected void addRequestHeaders(HessianConnection conn) {
        conn.addHeader("token", token);
        super.addRequestHeaders(conn);
    }
}
