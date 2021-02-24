package com.lqh;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(EurekaServerApplication.class, args);
        String[] defaultProfiles = context.getEnvironment().getDefaultProfiles();
        for (String defaultProfile : defaultProfiles) {
            System.out.println("当前profile是： " + defaultProfile);
        }
    }
}
