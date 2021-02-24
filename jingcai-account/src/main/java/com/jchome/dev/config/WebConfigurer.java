package com.lqh.dev.config;

import com.lqh.dev.interceptors.LoginInterceptor;
import com.lqh.dev.listener.CustomerHttpSessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.EventListener;

/**
 * @author liqihua5
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurerAdapter {

    @Resource
    private LoginInterceptor loginInterceptor;

    /**
     * 这个方法配置静态资源 css js html
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
    }

    /**
     * 这个方法用来注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/register");

    }

    @SuppressWarnings({"rawtypes"})
    @Bean
    public ServletListenerRegistrationBean listenerRegistry() {
        ServletListenerRegistrationBean<EventListener> slr = new ServletListenerRegistrationBean<>();
        slr.setListener(new CustomerHttpSessionListener());
        return slr;
    }
}
