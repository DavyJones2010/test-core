package edu.xmu.test.hibernate.service;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.event.spi.LoadEvent;
import org.hibernate.event.spi.LoadEventListener;

public class MyLoadListener implements LoadEventListener {
	private static final Logger logger = Logger.getLogger(MyLoadListener.class);

	@Override
	public void onLoad(LoadEvent event, LoadType loadType) throws HibernateException {
		logger.info("ClassName: " + event.getEntityClassName() + ", EntityID: " + event.getEntityId() + ", LockMode: " + event.getLockMode());
	}
}
