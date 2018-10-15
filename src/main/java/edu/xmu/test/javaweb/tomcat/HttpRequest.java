package edu.xmu.test.javaweb.tomcat;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.descriptor.JspConfigDescriptor;

import org.apache.log4j.Logger;

import com.google.common.base.Joiner;

import edu.xmu.test.javaweb.tomcat.HttpServletProcessor.RequestLine;

public class HttpRequest extends HttpServletRequestAdapter {
	RequestLine reqLine;
	Map<String, String> headers;

	ServletContext servletContext;
	InputStream is;

	static final Logger logger = Logger.getLogger(HttpRequest.class);

	public HttpRequest() {
		super();
	}

	public HttpRequest(InputStream is) {
		this.is = is;
	}

	public HttpRequest(RequestLine reqLine, Map<String, String> headers) {
		this.reqLine = reqLine;
		this.headers = headers;
	}

	@Override
	public String getMethod() {
		return reqLine.requestMethod;
	}

	@Override
	public String getRequestURI() {
		return reqLine.requestUri;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return Collections.enumeration(headers.keySet());
	}

	@Override
	public String getHeader(String name) {
		return headers.get(name);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(reqLine.requestParams.keySet());
	}

	@Override
	public String getParameter(String name) {
		return reqLine.requestParams.get(name);
	}

	@Override
	public String getQueryString() {
		return Joiner.on('&').withKeyValueSeparator("=").join(reqLine.requestParams);
	}

	@Override
	public ServletContext getServletContext() {
		if (null == servletContext) {
			servletContext = new ServletContextAdapter() {
				@Override
				public String getContextPath() {
					return reqLine.requestUri;
				}

				@Override
				public String getRealPath(String path) {
					return "src/main/resources/javaweb/app";
				}
			};
		}
		return servletContext;
	}

	static class ServletContextAdapter implements ServletContext {

		@Override
		public String getContextPath() {
			return null;
		}

		@Override
		public ServletContext getContext(String uripath) {
			return null;
		}

		@Override
		public int getMajorVersion() {

			return 0;
		}

		@Override
		public int getMinorVersion() {

			return 0;
		}

		@Override
		public int getEffectiveMajorVersion() {

			return 0;
		}

		@Override
		public int getEffectiveMinorVersion() {

			return 0;
		}

		@Override
		public String getMimeType(String file) {

			return null;
		}

		@Override
		public Set<String> getResourcePaths(String path) {

			return null;
		}

		@Override
		public URL getResource(String path) throws MalformedURLException {

			return null;
		}

		@Override
		public InputStream getResourceAsStream(String path) {

			return null;
		}

		@Override
		public RequestDispatcher getRequestDispatcher(String path) {

			return null;
		}

		@Override
		public RequestDispatcher getNamedDispatcher(String name) {

			return null;
		}

		@Override
		public Servlet getServlet(String name) throws ServletException {

			return null;
		}

		@Override
		public Enumeration<Servlet> getServlets() {

			return null;
		}

		@Override
		public Enumeration<String> getServletNames() {

			return null;
		}

		@Override
		public void log(String msg) {

		}

		@Override
		public void log(Exception exception, String msg) {

		}

		@Override
		public void log(String message, Throwable throwable) {

		}

		@Override
		public String getRealPath(String path) {

			return null;
		}

		@Override
		public String getServerInfo() {

			return null;
		}

		@Override
		public String getInitParameter(String name) {

			return null;
		}

		@Override
		public Enumeration<String> getInitParameterNames() {

			return null;
		}

		@Override
		public boolean setInitParameter(String name, String value) {

			return false;
		}

		@Override
		public Object getAttribute(String name) {

			return null;
		}

		@Override
		public Enumeration<String> getAttributeNames() {

			return null;
		}

		@Override
		public void setAttribute(String name, Object object) {

		}

		@Override
		public void removeAttribute(String name) {

		}

		@Override
		public String getServletContextName() {

			return null;
		}

		@Override
		public Dynamic addServlet(String servletName, String className) {

			return null;
		}

		@Override
		public Dynamic addServlet(String servletName, Servlet servlet) {

			return null;
		}

		@Override
		public Dynamic addServlet(String servletName, Class<? extends Servlet> servletClass) {

			return null;
		}

		@Override
		public <T extends Servlet> T createServlet(Class<T> clazz) throws ServletException {

			return null;
		}

		@Override
		public ServletRegistration getServletRegistration(String servletName) {

			return null;
		}

		@Override
		public Map<String, ? extends ServletRegistration> getServletRegistrations() {

			return null;
		}

		@Override
		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, String className) {

			return null;
		}

		@Override
		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Filter filter) {

			return null;
		}

		@Override
		public javax.servlet.FilterRegistration.Dynamic addFilter(String filterName, Class<? extends Filter> filterClass) {

			return null;
		}

		@Override
		public <T extends Filter> T createFilter(Class<T> clazz) throws ServletException {

			return null;
		}

		@Override
		public FilterRegistration getFilterRegistration(String filterName) {

			return null;
		}

		@Override
		public Map<String, ? extends FilterRegistration> getFilterRegistrations() {

			return null;
		}

		@Override
		public SessionCookieConfig getSessionCookieConfig() {

			return null;
		}

		@Override
		public void setSessionTrackingModes(Set<SessionTrackingMode> sessionTrackingModes) {

		}

		@Override
		public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {

			return null;
		}

		@Override
		public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {

			return null;
		}

		@Override
		public void addListener(String className) {

		}

		@Override
		public <T extends EventListener> void addListener(T t) {

		}

		@Override
		public void addListener(Class<? extends EventListener> listenerClass) {

		}

		@Override
		public <T extends EventListener> T createListener(Class<T> clazz) throws ServletException {

			return null;
		}

		@Override
		public JspConfigDescriptor getJspConfigDescriptor() {

			return null;
		}

		@Override
		public ClassLoader getClassLoader() {

			return null;
		}

		@Override
		public void declareRoles(String... roleNames) {

		}

		@Override
		public String getVirtualServerName() {

			return null;
		}

	}
}
