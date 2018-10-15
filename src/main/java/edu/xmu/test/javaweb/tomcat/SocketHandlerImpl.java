package edu.xmu.test.javaweb.tomcat;

import java.net.ServerSocket;
import java.net.Socket;

@Deprecated
public class SocketHandlerImpl implements Runnable {
	static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	static final String WEB_ROOT = "src/main/resources/javaweb/app";

	Socket socket;
	ServerSocket server;

	public SocketHandlerImpl(Socket socket, ServerSocket server) {
		super();
		this.socket = socket;
		this.server = server;
	}

	@Override
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName() + " is handling request");

			HttpRequest req = new HttpRequest(socket.getInputStream());
			HttpResponse resp = new HttpResponse(socket.getOutputStream());

			String requestUri = req.getRequestURI();
			if (SHUTDOWN_COMMAND.equals(requestUri)) {
				// TODO: shutdown server
			} else if (requestUri.startsWith("/servlet/")) {
				new HttpServletResourceProcessor().service(req, resp);
			} else {
				new HttpStaticResourceProcessor().service(req, resp);
			}

			socket.shutdownInput();
			socket.shutdownOutput();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}