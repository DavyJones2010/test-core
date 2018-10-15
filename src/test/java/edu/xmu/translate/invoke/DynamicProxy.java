package edu.xmu.translate.invoke;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DynamicProxy {
	private static final Logger logger = Logger.getLogger(DynamicProxy.class);
	@SuppressWarnings("unchecked")
	@Test
	public void useProxyTest() {
		String str = "Hello world";
		LoggingInvocationHandler handler = new LoggingInvocationHandler(str);
		Comparable<String> obj = (Comparable<String>) Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				new Class[]{Comparable.class}, handler);
		obj.compareTo("Yes");
	}
	public static class LoggingInvocationHandler implements InvocationHandler {
		private Object targetObject;

		public LoggingInvocationHandler(Object targetObject) {
			this.targetObject = targetObject;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			logger.info(String.format("Invoking method: [%s], args: [%s]",
					method.getName(), Lists.newArrayList(args)));

			return method.invoke(targetObject, args);
		}
	}

	public static interface GreetV1 {
		String greet(String name, String gender) throws IOException;
	}
	public static interface GreetV2 {
		String greet(String username);
	}
	public class GreetAdapter implements InvocationHandler {
		private GreetV1 greetInstance;

		public GreetAdapter(GreetV1 greetInstance) {
			super();
			this.greetInstance = greetInstance;
		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			String methodName = method.getName();
			if ("greet".equals(methodName)) {
				String username = (String) args[0];
				String name = lookupName(username);
				String gender = lookupGender(username);
				logger.info(proxy.getClass());
				// ((GreetV2)proxy).greet(username);
				try {
					return greetInstance.greet(name, gender);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				return method.invoke(greetInstance, args);
			}
		}
		private String lookupGender(String username) {
			// Dummy
			return "Male";
		}

		private String lookupName(String username) {
			// Dummy
			return "Yang";
		}
	}
	@Test
	public void adapterTest() {
		GreetAdapter adapter = new GreetAdapter(new GreetV1() {
			@Override
			public String greet(String name, String gender) throws IOException {
				return String.format("Hello %s[%s]", name, gender);
			}
		});
		GreetV2 greetV2 = (GreetV2) Proxy.newProxyInstance(this.getClass()
				.getClassLoader(), new Class[]{GreetV2.class}, adapter);
		assertEquals("Hello Yang[Male]", greetV2.greet("Yang, Kunlun"));
		logger.info(greetV2.getClass());
	}
}
