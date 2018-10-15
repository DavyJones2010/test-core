package edu.xmu.test.javaweb.tomcat;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class HttpServletResourceProcessor {
	static Map<String, HttpServlet> servletMapping = Maps.newConcurrentMap();
	static {
		// TODO: Instaintiate servlet instance using mapping config file like "web.xml"
		servletMapping.put("HttpSampleServlet", new HttpSampleServlet());
	}

	public void service(HttpServletRequest req, HttpServletResponse resp) {
		ServletContext context = req.getServletContext();

		// /servlet/HelloWorldServlet
		String path = context.getContextPath();
		String[] tokens = StringUtils.split(path, "/");
		Preconditions.checkArgument(2 == tokens.length);

		Servlet servletInstance = servletMapping.get(tokens[1].trim());
		try {
			servletInstance.service(req, resp);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
}
