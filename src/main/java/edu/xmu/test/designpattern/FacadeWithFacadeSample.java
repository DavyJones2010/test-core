package edu.xmu.test.designpattern;

import java.lang.reflect.Method;
import java.util.Map;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;

public class FacadeWithFacadeSample {

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

	/**
	 * This class is implemented by container <br>
	 * As it is part of facade, it would be safe when used by user/client <br>
	 */
	public static class RequestFacade implements Request {
		Request req;

		public RequestFacade(Request req) {
			this.req = req;
		}

		@Override
		public String getParameter(String str) {
			return req.getParameter(str);
		}
	}

	public static class Container {
		public void start(String reqStr) {
			RequestImpl req = new RequestImpl();
			req.setParamMap(Splitter.on('&').withKeyValueSeparator('=').split(reqStr));
			RequestFacade reqFacade = new RequestFacade(req);
			try {
				/*
				 * This is just a mockup for container <br>
				 * 1) The class name should be loaded from web.xml <br>
				 * 2) The class should be loaded with a new seprate class loader <br>
				 */
				Class<?> clazz = Class.forName("edu.xmu.test.designpattern.FacadeWithFacadeSample$ServletImpl");

				Object target = clazz.newInstance();
				Method m = clazz.getMethod("service", new Class[] { Request.class });

				// Container pass the facade to client
				m.invoke(target, new Object[] { reqFacade });
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
			 * As the container used facade in its impl, user can only downcast Request to RequestFacade <br>
			 * And all methods in RequestFacade are safe for container even if it is used by client<br>
			 */
			Map<String, String> newHashMap = Maps.newHashMap();
			newHashMap.put("username", "tor_jan");
			/*
			 * ClassCastException would be thrown
			 */
			// ((RequestImpl) req).setParamMap(newHashMap);

			username = req.getParameter("username");
			System.out.println("Unsafe username: " + username);
		}
	}

	public static void main(String[] args) {
		Container c = new Container();
		c.start("username=yang&password=yes");
	}
}
