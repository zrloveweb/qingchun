package com.xuemei.weixin.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 薛梅
 * @CreatedDate 2017/11/3 10:36
 */
@Aspect
@Component
public class ExceptionAop {

    private Logger logger = LoggerFactory.getLogger(ExceptionAop.class);

    @Around("execution(* com.xuemei.weixin.controller.*.*(..))")
    public Object handlerException(ProceedingJoinPoint point) throws IllegalAccessException, InstantiationException {
        Object proceed;
        Signature signature = point.getSignature();
        //获取返回的名字
        try {
            proceed = point.proceed();
        } catch (Throwable throwable) {
            logger.error("THREEDOTS WEB APP EXCEPTION:", throwable);
            Class<?> returnType = ((MethodSignature) signature).getReturnType();
            try {
                proceed = returnType.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                proceed = null;
            }
        }
        return proceed;
    }
}
