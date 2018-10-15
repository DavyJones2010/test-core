package edu.xmu.test.javaweb.tomcat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import org.apache.log4j.Logger;

public class HttpConnector implements Runnable {
	static final Logger logger = Logger.getLogger(HttpConnector.class);

	boolean stopped = false;
	String scheme = "http";
	private static Properties serverProperties = new Properties();
	static {
		try {
			Reader reader = new FileReader(new File("src/main/resources/javaweb/myserver.properties"));
			serverProperties.load(reader);
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getScheme() {
		return scheme;
	}

	@Override
	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(Integer.parseInt(serverProperties.getProperty("server.portnum")));
			while (!stopped) {
				Socket socket = serverSocket.accept();
				logger.info("Server accepted socket: " + socket);
				new HttpServletProcessor(this).process(socket);
			}
			serverSocket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
