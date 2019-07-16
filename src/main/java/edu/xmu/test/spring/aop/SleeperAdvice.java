package edu.xmu.test.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SleeperAdvice {

	@Before("execution(* edu.xmu.test.spring.aop.Human.*(..) )")
	public void before(JoinPoint jp) throws Throwable {
		System.out.println("Before sleep, we should take off our clothes");
	}

	@AfterReturning(value="execution(* edu.xmu.test.spring.aop.Human.*(..) )", returning = "result")
	public void afterReturning(JoinPoint jp, Object result) throws Throwable {
		System.out.println("After sleep, we should take on our clothes");
	}

	@Around(value = "execution(* edu.xmu.test.spring.aop.Human.*(..) )")
	public Object invoke(ProceedingJoinPoint jp) throws Throwable {
		System.out.println("Before sleep, we should take in some water");
		Object returnVal = jp.proceed();
		System.out.println("After sleep, we should take a bath");
		return returnVal;
	}

}
