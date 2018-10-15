package edu.xmu.test.javaweb.netty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	// 定义请求服务端口
	private static final int PORT = 6489;

	public static void main(String[] args) throws InterruptedException {
		// 创建客户端连接器.
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("abc", new LoggingFilter());
		connector.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

		connector.getFilterChain().addLast("print2", new IoFilterAdapter() {
			@Override
			public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest)
					throws Exception {
				System.out.println("before print2 filter messageSent, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + writeRequest.getMessage());
				super.messageSent(nextFilter, session, writeRequest);
				System.out.println("after print2 filter messageSent, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + writeRequest.getMessage());
			}

			@Override
			public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
				System.out.println("before print2 filter messageReceived, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + message);
				super.messageReceived(nextFilter, session, message);
				System.out.println("after print2 filter messageReceived, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + message);
			}
		});
		connector.getFilterChain().addLast("print", new IoFilterAdapter() {
			@Override
			public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest)
					throws Exception {
				System.out.println("before print filter messageSent, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + writeRequest.getMessage());
				super.messageSent(nextFilter, session, writeRequest);
				System.out.println("after print filter messageSent, remoteAddr=" + session.getRemoteAddress().toString()
						+ " msg=" + writeRequest.getMessage());
			}

			@Override
			public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
				System.out.println("before print filter messageReceived, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + message);
				super.messageReceived(nextFilter, session, message);
				System.out.println("after print filter messageReceived, remoteAddr="
						+ session.getRemoteAddress().toString() + " msg=" + message);
			}
		});

		// 设置连接超时检查时间
		connector.setConnectTimeoutCheckInterval(30);
		connector.setHandler(new IoHandlerAdapter() {
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println(
						"messageReceived, remoteAddr=" + session.getRemoteAddress().toString() + " msg=" + message);
			}

			public void messageSent(IoSession session, Object message) throws Exception {
				System.out.println(
						"messageSent, remoteAddr=" + session.getRemoteAddress().toString() + " msg=" + message);
			}
		});

		// 建立连接
		ConnectFuture cf = connector.connect(new InetSocketAddress("localhost", PORT));
		// 等待连接创建完成
		cf.awaitUninterruptibly();

		cf.getSession().write("Hi Server!");
		cf.getSession().write("quit");
		Thread.sleep(4L);
		// 等待连接断开
		cf.getSession().getCloseFuture().awaitUninterruptibly();
		// 释放连接
		connector.dispose();
	}
}
