package edu.xmu.test.designpattern;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A mockup for transformed Observer pattern used in Lifecycle management in Tomcat<br/>
 * All the listener related function is delegated to {@link LifecycleSupport}
 */
public class ObserverOfLifecycle {

	static class Lifecycle {
		LifecycleSupport support;

		public Lifecycle() {
			support = new LifecycleSupport(this);
		}

		public void addListener(LifecycleListener listener) {
			support.addListener(listener);
		}

		public void removeListener(LifecycleListener listener) {
			support.removeListener(listener);
		}

	}

	static class LifecycleEvent {
		String eventType;
		Object eventPayload;
		Lifecycle eventSource;

		public LifecycleEvent(String eventType, Object eventPayload, Lifecycle eventSource) {
			super();
			this.eventType = eventType;
			this.eventPayload = eventPayload;
			this.eventSource = eventSource;
		}

	}

	static class LifecycleListener {
		public void acceptEvent(LifecycleEvent event) {
			// some process
		}
	}

	/**
	 * This support class is used to extract listener specified logic out
	 */
	static class LifecycleSupport {
		Lifecycle lifecycle;
		List<LifecycleListener> listeners = Lists.newArrayList();

		public LifecycleSupport(Lifecycle lifecycle) {
			super();
			this.lifecycle = lifecycle;
		}

		public void addListener(LifecycleListener listener) {
			listeners.add(listener);
		}

		public void removeListener(LifecycleListener listener) {
			listeners.remove(listener);
		}

		public void fireEvent(String eventType, Object eventPayload) {
			LifecycleEvent event = new LifecycleEvent(eventType, eventPayload, lifecycle);
			for (LifecycleListener listener : listeners) {
				listener.acceptEvent(event);
			}
		}
	}
}
