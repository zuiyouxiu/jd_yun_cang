package com.lqh.dev.mytransactional.aspect;

import com.lqh.dev.mytransactional.annotation.MyTransactional;
import com.lqh.dev.mytransactional.transactional.LbTransaction;
import com.lqh.dev.mytransactional.transactional.LbTransactionManager;
import com.lqh.dev.mytransactional.transactional.TransactionType;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MyTransactionAspect implements Ordered {

    @Around("@annotation(com.lqh.dev.mytransactional.annotation.MyTransactional)")
    public void invoke(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        MyTransactional myTransactional = method.getAnnotation(MyTransactional.class);

        String groupId = "";
        if (myTransactional.isStart()) {
            //创建事务组
            groupId = LbTransactionManager.createLbTransactionGroup();
        }else {
            //todo ?

        }
        //添加事务到事务组
        LbTransaction transaction = LbTransactionManager.createTransaction(groupId);
        try {
            joinPoint.proceed();
            transaction.setTransactionType(TransactionType.COMMIT);
        } catch (Throwable throwable) {
            transaction.setTransactionType(TransactionType.ROLLBACK);
            throwable.printStackTrace();
        }

        LbTransactionManager.addLbTransaction(transaction,myTransactional.isEnd());
    }

    //我自己的@mytransactional 最后执行，优先执行spring的逻辑
    @Override
    public int getOrder() {
        return 1000;
    }
}
