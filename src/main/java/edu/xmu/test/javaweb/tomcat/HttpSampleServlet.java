package edu.xmu.test.javaweb.tomcat;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HttpSampleServlet extends HttpServlet {
	static final Logger logger = Logger.getLogger(HttpSampleServlet.class);

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		switch (req.getMethod()) {
		case "GET":
			doGet(req, resp);
			break;
		case "POST":
			doPost(req, resp);
			break;
		default:
			throw new RuntimeException();
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		logger.info("doGet " + this.getClass().getSimpleName());
		PrintWriter writer = resp.getWriter();

		writer.println("<b>Headers</b>: <br/>");
		Enumeration<String> headerNames = req.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			writer.println(key + ": " + req.getHeader(key) + "<br/>");
		}

		writer.println("<b>Method</b>: <br/>");
		writer.println(req.getMethod() + "<br/>");

		writer.println("<b>Parameters</b>: <br/>");
		Enumeration<String> paramNames = req.getParameterNames();
		while (paramNames.hasMoreElements()) {
			String key = paramNames.nextElement();
			writer.println(key + ": " + req.getParameter(key) + "<br/>");
		}

		writer.println("Query String<br/>");
		writer.print(req.getQueryString() + "<br/>");

		writer.println("Request URI<br/>");
		writer.print(req.getRequestURI() + "<br/>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.getWriter().println("doPost " + this.getClass().getSimpleName());
		logger.info("doPost " + this.getClass().getSimpleName());
	}
}
