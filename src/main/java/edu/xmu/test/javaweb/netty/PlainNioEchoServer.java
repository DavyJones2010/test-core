package edu.xmu.test.javaweb.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class PlainNioEchoServer {
	public void serve(int port) throws IOException {
		System.out.println("Listening for connections on port: " + port);
		ServerSocketChannel channel = ServerSocketChannel.open();
		ServerSocket serverSocket = channel.socket();
		serverSocket.bind(new InetSocketAddress(port));

		channel.configureBlocking(false);
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_ACCEPT);

		while (true) {
			selector.select();

			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iterator = selectedKeys.iterator();
			while (iterator.hasNext()) {
				SelectionKey key = iterator.next();
				iterator.remove();
				if (key.isAcceptable()) {
					ServerSocketChannel server = (ServerSocketChannel) key.channel();
					SocketChannel client = server.accept();

				}
			}
		}
	}

	public static void main(String[] args) {

	}
}
