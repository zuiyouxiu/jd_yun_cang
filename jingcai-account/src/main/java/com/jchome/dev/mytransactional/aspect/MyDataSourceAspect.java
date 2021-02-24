package com.lqh.dev.mytransactional.aspect;


import com.lqh.dev.mytransactional.connection.MyConnection;
import com.lqh.dev.service.IAccountService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Connection;

@Aspect
@Component
public class MyDataSourceAspect {

    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection around(ProceedingJoinPoint joinPoint) {
        //拿到spring事务的控制权

        Connection connection = null;
        try {
            connection = (Connection) joinPoint.proceed();
            return new MyConnection(connection);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}
