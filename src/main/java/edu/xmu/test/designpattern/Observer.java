package edu.xmu.test.designpattern;

import java.util.List;

import com.google.common.collect.Lists;

public class Observer {
	static interface Subject {
		void addListener(Listener listener);

		void removeListener(Listener listener);

		void fireWindowCreatedEvent();

		void fireWindowDestroyedEvent();
	}

	static class SubjectImpl implements Subject {
		List<Listener> listeners = Lists.newArrayList();

		public void fireWindowCreatedEvent() {
			Event e = new Event(this, "windowCreated");
			listeners.stream().forEach(l -> l.handleEvent(e));
		}

		public void fireWindowDestroyedEvent() {
			Event e = new Event(this, "windowDestroyed");
			listeners.stream().forEach(l -> l.handleEvent(e));
		}

		@Override
		public void addListener(Listener listener) {
			listeners.add(listener);
		}

		@Override
		public void removeListener(Listener listener) {
			listeners.remove(listener);
		}
	}

	static class Event {
		Subject subject;
		String eventName;

		public Event(Subject subject, String eventName) {
			this.subject = subject;
			this.eventName = eventName;
		}
	}

	static interface Listener {
		void handleEvent(Event e);
	}

	public static void main(String[] args) {
		Subject s = new SubjectImpl();
		s.addListener(e -> {
			System.out.println("Listener1 handled event: " + e.eventName);
		});
		s.addListener(e -> {
			System.out.println("Listener2 handled event: " + e.eventName);
		});

		s.fireWindowCreatedEvent();
		s.fireWindowDestroyedEvent();
	}
}
