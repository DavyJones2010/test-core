package edu.xmu.test.designpattern;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class FacadeWithoutFacadeSample {

	/**
	 * This class is a part of SPI
	 */
	public static interface Request {
		public String getParameter(String str);
	}

	/**
	 * This class is a part of SPI
	 */
	public static interface Servlet {
		public void service(Request req);
	}

	/**
	 * This class is implemented by container and should be visible only in container scope
	 */
	public static class RequestImpl implements Request {
		private Map<String, String> paramMap;

		@Override
		public String getParameter(String str) {
			return paramMap.get(str);
		}

		public void setParamMap(Map<String, String> paramMap) {
			this.paramMap = paramMap;
		}
	}

	public static class Container {
		public void start(String reqStr) {
			RequestImpl req = new RequestImpl();
			req.setParamMap(Splitter.on('&').withKeyValueSeparator('=').split(reqStr));

			try {
				/*
				 * This is just a mockup for container <br>
				 * 1) The class name should be loaded from web.xml <br>
				 * 2) The class should be loaded with a new seprate class loader <br>
				 */
				Class<?> clazz = Class.forName("edu.xmu.test.designpattern.FacadeWithoutFacadeSample$ServletImpl");

				Object target = clazz.newInstance();
				Method m = clazz.getMethod("service", new Class[] { Request.class });
				m.invoke(target, new Object[] { req });
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This class is implemented by user
	 */
	public static class ServletImpl implements Servlet {
		@Override
		public void service(Request req) {
			String username = req.getParameter("username");
			System.out.println("Safely username: " + username);

			/*
			 * If we do not use facade in container impl, user can easily downcast Request to RequestImpl and damage the container
			 */
			Map<String, String> newHashMap = Maps.newHashMap();
			newHashMap.put("username", "tor_jan");
			((RequestImpl) req).setParamMap(newHashMap);

			username = req.getParameter("username");
			System.out.println("Unsafe username: " + username);
		}
	}

	public static void main(String[] args) {
		Container c = new Container();
		c.start("username=yang&password=yes");
	}
}
