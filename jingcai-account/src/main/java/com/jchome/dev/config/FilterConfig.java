package com.lqh.dev.config;

import com.lqh.dev.filters.Filter01;
import com.lqh.dev.filters.Filter02;
import com.lqh.dev.filters.FrontPreAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean filter01() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Filter01());
        registration.addUrlPatterns("/account/find/name");
        registration.setName("filter_02");
        registration.setOrder(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean filter02() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new Filter02());
        registration.addUrlPatterns("/account/*");
        registration.setName("filter_01");
        registration.setOrder(2);
        return registration;
    }

    /*@Bean
    public FilterRegistrationBean filter04() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ManPreAuthFilter());
        registration.addUrlPatterns("/*");
        registration.setName("filter_04");
        registration.setOrder(5);
        return registration;
    }*/

    @Bean
    public FilterRegistrationBean filter05() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new FrontPreAuthFilter());
        registration.addUrlPatterns("/*");
        registration.setName("filter_05");
        registration.setOrder(4);
        return registration;
    }
}
