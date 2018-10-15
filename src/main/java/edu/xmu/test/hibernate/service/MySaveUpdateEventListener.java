package edu.xmu.test.hibernate.service;

import org.hibernate.event.internal.DefaultSaveOrUpdateEventListener;
import org.hibernate.event.spi.SaveOrUpdateEvent;

public class MySaveUpdateEventListener extends DefaultSaveOrUpdateEventListener {
	@Override
	public void onSaveOrUpdate(SaveOrUpdateEvent event) {
		System.out.println("onSaveOrUpdate" + event);
	}
}
