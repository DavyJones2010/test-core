package edu.xmu.test.javaweb.servlet;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class MyServletContextListener implements ServletContextListener, ServletContextAttributeListener {
	static Logger logger = Logger.getLogger(MyServletContextListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("Start MyServletContextListener: " + this.getClass().getSimpleName() + ", creating DB connection");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Destroy MyServletContextListener: " + this.getClass().getSimpleName() + ", releasing DB connection");
	}

	@Override
	public void attributeAdded(ServletContextAttributeEvent event) {
		logger.info("attributeAdded: " + event.getName() + ", " + event.getValue());
	}

	@Override
	public void attributeRemoved(ServletContextAttributeEvent event) {
		logger.info("attributeRemoved: " + event.getName() + ", " + event.getValue());
	}

	@Override
	public void attributeReplaced(ServletContextAttributeEvent event) {
		logger.info("attributeReplaced: " + event.getName() + ", " + event.getValue());
	}

}
