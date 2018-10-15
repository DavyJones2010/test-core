package edu.xmu.test.javaweb.servlet;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

public class MySessionContextListener implements HttpSessionListener, HttpSessionAttributeListener {
	static Logger logger = Logger.getLogger(MySessionContextListener.class);

	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		logger.info(event.getSession().getId() + " attributeAdded: " + event.getName() + ", " + event.getValue());
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		logger.info(event.getSession().getId() + " attributeRemoved: " + event.getName() + ", " + event.getValue());
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		logger.info(event.getSession().getId() + " attributeReplaced: " + event.getName() + ", " + event.getValue());
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info(se.getSession().getId() + " sessionCreated");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		logger.info(se.getSession().getId() + " sessionDestroyed");
	}

}
