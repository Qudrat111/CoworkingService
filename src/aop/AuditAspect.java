package aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AuditAspect {

    @Pointcut("execution(* Service.*.*(..))")
    public void serviceMethods() {}

    @After("serviceMethods()")
    public void logServiceAccess() {
        // Logging user actions
        System.out.println("A method in the service was executed.");
    }
}
