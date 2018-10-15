package edu.xmu.test.javaweb.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.google.common.net.HttpHeaders;

public class LogFilter implements Filter {
	static Logger logger = Logger.getLogger(LogFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		StringBuilder info = new StringBuilder("IP Addr: " + request.getRemoteAddr() + "; Request URL: " + ((HttpServletRequest) request).getRequestURL());
		if (null != ((HttpServletRequest) request).getHeader(HttpHeaders.REFERER)) {
			info.append("; Request From: " + ((HttpServletRequest) request).getHeader(HttpHeaders.REFERER));
		}
		logger.info(info.toString());
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
