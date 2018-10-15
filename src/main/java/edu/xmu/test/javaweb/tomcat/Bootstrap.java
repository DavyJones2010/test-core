package edu.xmu.test.javaweb.tomcat;

public final class Bootstrap {
	public static void main(String[] args) throws InterruptedException {
		HttpConnector conn = new HttpConnector();
		conn.run();
		// ExecutorService executors = Executors.newCachedThreadPool();
		// executors.submit(conn);
	}
}
