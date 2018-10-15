package edu.xmu.test.javaweb.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class ResponseServletFilter2 implements Filter {
	static Logger logger = Logger.getLogger(ResponseServletFilter2.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String name = filterConfig.getInitParameter("first_name") + ", " + filterConfig.getInitParameter("last_name");
		logger.info("Start init: " + this.getClass().getSimpleName() + ", username: " + name);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		logger.info("Before doFilter: " + this.getClass().getSimpleName());
		chain.doFilter(request, response);
		logger.info("After doFilter: " + this.getClass().getSimpleName());
	}

	@Override
	public void destroy() {
		logger.info("Start destroy: " + this.getClass().getSimpleName());
	}

}
