package edu.xmu.test.javaweb.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaServer {
	// 定义监听端口
	private static final int PORT = 6489;

	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(2048);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		// 设置日志记录器
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		// 设置编码过滤器
		acceptor.getFilterChain().addLast("codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		acceptor.getFilterChain().addLast("print", new IoFilterAdapter() {
			@Override
			public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest)
					throws Exception {
				System.out.println("filter messageSent, remoteAddr=" + session.getRemoteAddress().toString() + " msg="
						+ writeRequest.getMessage());
				super.messageSent(nextFilter, session, writeRequest);
			}

			@Override
			public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
				System.out.println("filter messageReceived, remoteAddr=" + session.getRemoteAddress().toString()
						+ " msg=" + message);
				super.messageReceived(nextFilter, session, message);
			}
		});

		// 指定业务逻辑处理器

		acceptor.setHandler(new IoHandlerAdapter() {
			@Override
			public void sessionCreated(IoSession session) throws Exception {
				System.out.println("sessionCreated, remoteAddr=" + session.getRemoteAddress().toString());
			}

			@Override
			public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
				System.out.println("exceptionCaught, remoteAddr=" + session.getRemoteAddress().toString() + " e="
						+ cause.getStackTrace());
				cause.printStackTrace();
			}

			@Override
			public void messageReceived(IoSession session, Object message) throws Exception {
				System.out.println(
						"messageReceived, remoteAddr=" + session.getRemoteAddress().toString() + " message=" + message);
				session.write(StringUtils.reverse(message.toString()));
			}

			@Override
			public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
				System.out.println(
						"sessionIdle, remoteAddr=" + session.getRemoteAddress().toString() + " status=" + status);
			}

			@Override
			public void sessionClosed(IoSession session) throws Exception {
				System.out.println("sessionClosed, remoteAddr=" + session.getRemoteAddress().toString());
			}
		});
		// 设置端口号
		acceptor.bind(new InetSocketAddress(PORT));
		// 启动监听线程
		// acceptor.bind();
	}
}
