package com.occultus.learncase.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@EnableAspectJAutoProxy
public class LogAspect {

    @Pointcut("execution(* com.occultus.learncase.aop.service.*.*(..))")
    private void pointCutMethod() {
    }

    @Before("pointCutMethod()")
    public void doBefore() {
        log.warn("do before");
    }

    @AfterReturning(value = "pointCutMethod()", returning = "result")
    public void doAfterReturning(String result) {
        log.warn("do after returning :{}", result);
    }

    @After("pointCutMethod()")
    public void doAfter() {
        log.warn("do after");
    }

    @Around("pointCutMethod()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        log.warn("---------Around----------");
        log.warn("Around: input");
        Object o = pjp.proceed();
        log.warn("Around: output");
        log.warn("---------Around----------");
        return o;
    }

    @AfterThrowing(pointcut = "pointCutMethod()", throwing = "e")
    public void doAfterThrowing(Exception e) {
        log.warn("do after throwing :{}", e.getMessage());
    }
}
