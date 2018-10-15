package edu.xmu.test.javaweb.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.common.net.HttpHeaders;

public class RefreshServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(RefreshServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setIntHeader(HttpHeaders.REFRESH, 5);
		resp.getWriter().write(Calendar.getInstance().toString());
	}

}
