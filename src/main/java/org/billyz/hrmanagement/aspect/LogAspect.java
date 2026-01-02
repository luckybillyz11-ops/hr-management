package org.billyz.hrmanagement.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.billyz.hrmanagement.annotation.LogRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(org.billyz.hrmanagement.annotation.LogRecord)")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        LogRecord logRecord = method.getAnnotation(LogRecord.class);
        String operation = logRecord.value();

        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;
        if (request != null) {
            log.info("\n"+
                    "操作描述: {}\n" +
                    "URL: {}\n" +
                    "HTTP方法: {}\n" +
                    "类方法: {}.{}\n" +
                    "参数: {}\n",
                    operation, request.getRequestURL(), request.getMethod(), className, methodName, Arrays.toString(args));
        }
        Object result;
        try {
            result = joinPoint.proceed(args);
        } catch (Exception e) {
            log.error("操作失败：{},异常信息：{}", operation, e);
            throw e;
        }
        log.info("返回结果 : {}", result);

    return  result;
    }
}
