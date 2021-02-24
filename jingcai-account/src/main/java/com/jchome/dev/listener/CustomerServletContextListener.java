package com.lqh.dev.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener("servlet_context_listener")
public class CustomerServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("context域创建了...");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        log.info("context域销毁了...");
    }
}
