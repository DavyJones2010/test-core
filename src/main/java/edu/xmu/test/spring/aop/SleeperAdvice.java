package edu.xmu.test.spring.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

public class SleeperAdvice implements MethodBeforeAdvice, AfterReturningAdvice, MethodInterceptor {

	@Override
	public void before(Method method, Object[] args, Object target) throws Throwable {
		System.out.println("Before sleep, we should take off our clothes");
	}

	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
		System.out.println("After sleep, we should take on our clothes");
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		System.out.println("Before sleep, we should take in some water");
		Object returnVal = invocation.proceed();
		System.out.println("After sleep, we should take a bath");
		return returnVal;
	}

}
