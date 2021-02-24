package com.lqh.dev.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Slf4j
public class CustomerHttpSessionListener implements HttpSessionListener {

    public static int count;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        log.info("创建session");
        count++;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        log.info("销毁session");
    }
}
