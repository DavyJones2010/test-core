package edu.xmu.test.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class CRUDbleAdviceAnnotationBasedImpl {

	@Pointcut("execution(* edu.xmu.test.spring.aop.CRUDble.*(..) )")
	public void anyMethod() {
		System.out.println("anyMethod");
	}

	// @Before("execution(* edu.xmu.test.spring.aop.CRUDble.*(..) )")
	@Before("anyMethod()")
	public void doBefore(JoinPoint jp) {
		System.out.println("start doBefore, targetClass: " + jp.getTarget().getClass() + ", targetMethod: " + jp.getSignature().getName() + ", args: " + jp.getArgs());
	}

	// @AfterReturning(value = "execution(* edu.xmu.test.spring.aop.CRUDble.*(..) )", returning = "result")
	@AfterReturning(value = "anyMethod()", returning = "result")
	public Object doAfter(JoinPoint jp, Object result) {
		System.out.println("start doAfter, targetClass: " + jp.getTarget().getClass() + ", targetMethod: " + jp.getSignature().getName() + ", args: " + jp.getArgs() + ", result: "
				+ result);
		return result;
	}

	// @Around(value = "execution(* edu.xmu.test.spring.aop.CRUDble.*(..) )")
	@Around(value = "anyMethod()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("start doAround, targetClass: " + pjp.getTarget().getClass() + ", targetMethod: " + pjp.getSignature().getName() + ", args: " + pjp.getArgs());
		Object result = pjp.proceed();
		System.out.println("finished doAround, targetClass: " + pjp.getTarget().getClass() + ", targetMethod: " + pjp.getSignature().getName() + ", args: " + pjp.getArgs()
				+ ", result: " + result);
		return result;
	}
}
