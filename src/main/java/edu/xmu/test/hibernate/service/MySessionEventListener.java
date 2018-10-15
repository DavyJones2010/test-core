package edu.xmu.test.hibernate.service;

import org.hibernate.engine.internal.SessionEventListenerManagerImpl;

public class MySessionEventListener extends SessionEventListenerManagerImpl {
	@Override
	public void flushStart() {
		System.out.println("--flushStart--");
	}

	@Override
	public void flushEnd(int numberOfEntities, int numberOfCollections) {
		System.out.println("--flushEnd--numberOfEntities:" + numberOfEntities);
	}

	@Override
	public void dirtyCalculationStart() {
		System.out.println("--dirtyCalculationStart--");
	}

	@Override
	public void dirtyCalculationEnd(boolean dirty) {
		System.out.println("--dirtyCalculationEnd--isDirty:" + dirty);

	}

	@Override
	public void end() {
		System.out.println("End");
	}
}
