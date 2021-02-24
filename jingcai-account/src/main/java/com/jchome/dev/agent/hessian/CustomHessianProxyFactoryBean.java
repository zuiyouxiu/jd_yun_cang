package com.lqh.dev.agent.hessian;

import org.springframework.remoting.caucho.HessianProxyFactoryBean;

public class CustomHessianProxyFactoryBean extends HessianProxyFactoryBean {

    private String token;

    private CustomHessianProxyFactory customHessianProxyFactory;

    @Override
    public void afterPropertiesSet() {
        customHessianProxyFactory.setToken(token);
        setProxyFactory(customHessianProxyFactory);
        super.afterPropertiesSet();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CustomHessianProxyFactory getCustomHessianProxyFactory() {
        return customHessianProxyFactory;
    }

    public void setCustomHessianProxyFactory(CustomHessianProxyFactory customHessianProxyFactory) {
        this.customHessianProxyFactory = customHessianProxyFactory;
    }
}
