package edu.xmu.test.javaweb.servlet;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class HelloServlet extends HttpServlet {
	static Logger logger = Logger.getLogger(HelloServlet.class);

	@Override
	public void init() throws ServletException {
		super.init();
		logger.info("Start init: " + this.getClass().getSimpleName() + " without param");
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		logger.info("Start init: " + this.getClass().getSimpleName() + " with servlet-param: [first_name: " + config.getInitParameter("first_name") + ", servletName: "
				+ config.getServletName() + "], and context-param: [user_name: " + config.getServletContext().getInitParameter("user_name") + "]");
		// super.init(config) will invoke init();
		// the result is: 1> init(ServletConfig) 2> init()
		super.init(config);
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		logger.info("Start service: " + this.getClass().getSimpleName());
		super.service(req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = req.getParameter("user_name");
		if (null == username && null != getServletContext().getAttribute("user_name")) {
			getServletContext().removeAttribute("user_name");
		} else {
			getServletContext().setAttribute("user_name", username);
		}
		HttpSession session = req.getSession(true);
		if (null == username && null != session.getAttribute("user_name")) {
			session.removeAttribute("user_name");
		} else if ("invalidate_session".equals(username)) {
			// destroy session
			session.invalidate();
		} else {
			session.setAttribute("user_name", username);
		}

		Writer writer = resp.getWriter();
		writer.write("Hello " + username);
		writer.close();
	}

	@Override
	public void destroy() {
		super.destroy();
		logger.info("Start destroy: " + this.getClass().getSimpleName());
	}
}
