package pl.training.shop.common.retry;

import lombok.extern.java.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
@Log
public class MethodExecutor {

    @Around("@annotation(Retry)")
    public Object execute(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        int retryNumber;
        int currentAttempt =0;
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = methodSignature.getMethod();
        retryNumber = method.getAnnotation(Retry.class).numberRetry();
        Throwable throwable;
        do  {
            currentAttempt++;
            log.info(String.format("%s execution attempt %d",proceedingJoinPoint.getSignature().getName(),currentAttempt));
            try {
                Object result= proceedingJoinPoint.proceed();
                return result;
            }
            catch (Throwable t) {
               throwable =t;
            }
        } while (retryNumber>currentAttempt);
        throw throwable;
    }
}
