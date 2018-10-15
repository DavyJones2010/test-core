package edu.xmu.test.javaweb.tomcat;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyHttpServer {
	private static ServerSocket server;
	private static Properties serverProperties = new Properties();

	public static void startServer() throws IOException {
		Reader reader = new FileReader(new File("src/main/resources/javaweb/myserver.properties"));
		serverProperties.load(reader);
		reader.close();
		server = new ServerSocket(Integer.parseInt(serverProperties.getProperty("server.portnum")));
	}

	public static void stopServer() {
		try {
			if (null != server && !server.isClosed()) {
				server.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error closing server");
		}
	}

	public static void main(String[] args) throws IOException {
		startServer();
		ExecutorService es = Executors.newCachedThreadPool();
		while (!server.isClosed()) {
			Socket socket = server.accept();
			System.out.println("Server accepted socket: " + socket);
			es.submit(new SocketHandlerImpl(socket, server));
		}
		es.shutdown();
		System.exit(0);
	}
}
