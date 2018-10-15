package edu.xmu.test.javaweb.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class HelloFormServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(HelloFormServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doGet");
		String referrer = req.getHeader("referer");
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		Writer writer = resp.getWriter();
		writer.write("Hello " + firstName + ", " + lastName + ". From " + referrer + ", target " + req.getRequestURL().toString());
		writer.close();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("doPost");
		String firstName = req.getParameter("first_name");
		String lastName = req.getParameter("last_name");
		Writer writer = resp.getWriter();
		writer.write("Yes " + firstName + ", " + lastName);
		writer.close();
	}

}
