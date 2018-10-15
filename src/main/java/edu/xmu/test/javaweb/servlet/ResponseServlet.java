package edu.xmu.test.javaweb.servlet;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ResponseServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(ResponseServlet.class);

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Cookie[] cookies = req.getCookies();
		boolean isUserNameSet = false;
		boolean isReqDateSet = false;
		for (Cookie cookie : cookies) {
			if ("user_name".equals(cookie.getName())) {
				logger.info("user_name: " + cookie.getValue());
				isUserNameSet = true;
			} else if ("req_date".equals(cookie.getName())) {
				logger.info("req_date: " + cookie.getValue());
				isReqDateSet = true;
			}
		}
		if (!isUserNameSet) {
			resp.addCookie(new Cookie("user_name", req.getParameter("first_name") + ", " + req.getParameter("last_name")));
		}
		if (!isReqDateSet) {
			resp.addCookie(new Cookie("req_date", Calendar.getInstance().getTimeInMillis() + ""));
		}
		if ("true".equals(req.getParameter("test_forward"))) {
			/*
			 * If the path begins with a "/" it is interpreted as relative to the {current context root} that is http://localhost:8080/test-core <br/>
			 */
			// forwarded to http://localhost:8080/test-core/get-form.jsp
			req.getRequestDispatcher("/get_form.jsp").forward(req, resp);
		}

		/*
		 * If the location is relative with a leading '/' the container interprets it as relative to the servlet container root. <br/>
		 * If the location is relative without a leading '/' the container interprets it as relative to the current request URI. <br/>
		 */
		if ("true".equals(req.getParameter("test_redirect"))) {
			// redirect to http://localhost:8080/get_form.jsp
			// resp.sendRedirect("/get_form.jsp");

			// redirect to http://localhost:8080/test-core/serlvet/get_form.jsp
			// resp.sendRedirect("get_form.jsp");

			// redirect to http://localhost:8080/test-core/get_form.jsp
			// req.getContextPath()-> "/test-core"
			resp.sendRedirect(req.getContextPath() + "/get_form.jsp");
		}
	}
}
