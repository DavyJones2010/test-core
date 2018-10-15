package edu.xmu.test.javax.log4j;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LocationInfo;
import org.junit.Test;

public class MDCTest {
	static Logger logger = Logger.getLogger(MDCTest.class);

	@Test
	public void mdcTest() throws InterruptedException {
		// The value in MDC is ThreadLocal
		// %X{USER_ID} will be automatically replaced with the value in MDC
		// %X{REQUEST_ID} will be automatically replaced with the value in MDC
		MDC.put("USER_ID", "Davy_Walker");
		MDC.put("REQUEST_ID", "1234567");
		logger.info("Hello world!");
		new Thread(new Runnable() {
			@Override
			public void run() {
				MDC.put("USER_ID", "Davy_Walker_" + Thread.currentThread().getId());
				MDC.put("REQUEST_ID", (int) (Math.random() * 10000));
				try {
					TimeUnit.MILLISECONDS.sleep((long) (1000 * Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("Hello world!");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				MDC.put("USER_ID", "Davy_Walker_" + Thread.currentThread().getId());
				MDC.put("REQUEST_ID", (int) (Math.random() * 10000));
				try {
					TimeUnit.MILLISECONDS.sleep((long) (1000 * Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("Hello world!");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				MDC.put("USER_ID", "Davy_Walker_" + Thread.currentThread().getId());
				MDC.put("REQUEST_ID", (int) (Math.random() * 10000));
				try {
					TimeUnit.MILLISECONDS.sleep((long) (1000 * Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("Hello world!");
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				MDC.put("USER_ID", "Davy_Walker_" + Thread.currentThread().getId());
				MDC.put("REQUEST_ID", (int) (Math.random() * 10000));
				try {
					TimeUnit.MILLISECONDS.sleep((long) (1000 * Math.random()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.info("Hello world!");
			}
		}).start();
		TimeUnit.MILLISECONDS.sleep(1000L);
		MDC.put("USER_ID", "Davy_Walker");
		MDC.put("REQUEST_ID", "2345678");
		logger.info("Hello world!");
	}

	void print(String s) {
		LocationInfo locationInfo = new LocationInfo(new Throwable(), "edu.xmu.test.javax.log4j.MDCTest.print");
		// 打印出的堆栈信息如下, LocationInfo会根据"edu.xmu.test.javax.log4j.MDCTest.print"关键字找第一个出现的位置, 打印出下一行信息
		/*
		 * java.lang.Throwable
	at edu.xmu.test.javax.log4j.MDCTest.print(MDCTest.java:80)
	at edu.xmu.test.javax.log4j.MDCTest.lineNoTest(MDCTest.java:87)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:497)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:47)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:44)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:271)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:50)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:238)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:63)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:236)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:53)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:229)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:309)
	at org.eclipse.jdt.internal.junit4.runner.JUnit4TestReference.run(JUnit4TestReference.java:86)
	at org.eclipse.jdt.internal.junit.runner.TestExecution.run(TestExecution.java:38)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:459)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.runTests(RemoteTestRunner.java:675)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.run(RemoteTestRunner.java:382)
	at org.eclipse.jdt.internal.junit.runner.RemoteTestRunner.main(RemoteTestRunner.java:192)
		 */
		System.out.println(locationInfo.fullInfo + ", " + s);
	}

	/**
	 * log4j内部使用的是通过{@link Throwable}对象来获取调用堆栈, 从而得到日志调用类名+方法+行号信息.
	 */
	@Test
	public void lineNoTest() {
		logger.info("wtf");
		print("sbsdd");
	}
}
