package edu.xmu.test.javaweb.tomcat;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.google.common.collect.Lists;

/**
 * A demo for the corporation between Connector and Processor in tomcat impl
 */
public class ConnectorAndProcessorSample {
	static class Socket {
	}

	static class Processor extends Thread {
		volatile boolean stopped = false;
		Connector connector;
		Socket socket;
		private boolean isSocketAvailableForProcessor = false;

		public Processor(Connector connector) {
			this.connector = connector;
		}

		@Override
		public void run() {
			while (!stopped) {
				Socket obj = await();
				if (null == obj) {
					continue;
				}
				process(obj);
				connector.recycleProcessor(this);
			}
		}

		private void process(Socket obj) {
			System.out.println(Thread.currentThread().getName() + " start to process socket: " + obj);
			try {
				Thread.sleep((long) (Math.random() * 1000L));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " finished process socket: " + obj);
		}

		/**
		 * Invoked by Connector Thread
		 */
		synchronized public void assign(Socket obj) {
			// connector thread wait for processor thread finishing process previous socket
			while (isSocketAvailableForProcessor) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.socket = obj;
			isSocketAvailableForProcessor = true;
			notifyAll();
		}

		/**
		 * Invoked by Processor Thread
		 */
		synchronized public Socket await() {
			// processor thread wait for connector thread assign available socket
			while (!isSocketAvailableForProcessor) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			// The reason we assign this.socket to local variable is that the field "this.socket" can then accept another socket without damaging current processor
			Socket socket = this.socket;
			isSocketAvailableForProcessor = false;
			notifyAll();
			return socket;
		}
	}

	static class Connector implements Runnable {
		/**
		 * processor pool, has to be threadsafe because it will be accessed by multiple processor threads simultaneously
		 */
		List<Processor> processors = Lists.newCopyOnWriteArrayList();
		BlockingQueue<Socket> socketChannel = new ArrayBlockingQueue<Socket>(5);
		private boolean stopped = false;

		public void init() {
			processors.add(new Processor(this));
			processors.add(new Processor(this));
			processors.add(new Processor(this));
			processors.add(new Processor(this));
			processors.stream().forEach(p -> p.start());
		}

		@Override
		public void run() {
			init();
			while (!stopped) {
				Socket socket = getSocket();
				Processor processor = getAvailableProcessor();
				if (null == processor) {
					// there is not enough processor in processor pool to handle http request, just ignore the request
					System.out.println("Socket: " + socket + " has been ignored");
				} else {
					// Connector thread will not be blocked by processor thread anymore
					processor.assign(socket);
				}
			}
		}

		/**
		 * This method will block current thread till the client send a request.
		 */
		private Socket getSocket() {
			try {
				return socketChannel.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		/**
		 * This method is meant to be called by client.
		 */
		public void putSocket(Socket socket) {
			try {
				socketChannel.put(socket);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		private void recycleProcessor(Processor processor) {
			processors.add(processor);
		}

		private Processor getAvailableProcessor() {
			if (processors.isEmpty()) {
				return null;
			} else {
				return processors.remove(0);
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Connector connector = new Connector();
		Thread t = new Thread(connector);
		t.start();
		connector.putSocket(new Socket());
		Thread.sleep(1000L);
		connector.putSocket(new Socket());
		connector.putSocket(new Socket());
		connector.putSocket(new Socket());
		connector.putSocket(new Socket());
		connector.putSocket(new Socket());
		connector.putSocket(new Socket());

	}
}
