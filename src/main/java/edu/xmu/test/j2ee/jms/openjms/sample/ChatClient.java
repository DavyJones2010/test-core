package edu.xmu.test.j2ee.jms.openjms.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * JMS chat root sample based on
 * <b>src/main/resources/mooc/jms/jms-chat-client.pdf</b>
 * 
 * @author davyjones
 *
 */
public class ChatClient {
	public static void main(String[] args) throws NamingException, JMSException {
		Context context = null;
		TopicConnectionFactory factory = null;
		TopicConnection connection = null;
		TopicSession pubSession = null;
		TopicSession subSession = null;
		Topic topic = null;
		TopicPublisher topicPublisher = null;
		TopicSubscriber topicSubscriber = null;
		String topicName = "topic1";
		String factoryName = "ConnectionFactory";

		// create the JNDI initial context.
		context = new InitialContext();

		// look up the ConnectionFactory
		factory = (TopicConnectionFactory) context.lookup(factoryName);
		topic = (Topic) context.lookup(topicName);

		// create the connection
		connection = factory.createTopicConnection();

		// create the session
		// 按照JMS规范,一个会话不能同时在一个以上的线程中运行.
		pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		topicPublisher = pubSession.createPublisher(topic);
		topicSubscriber = subSession.createSubscriber(topic, null, true);
		connection.start();

		Thread receiverThread = new Thread(new ReceiverTask(topicSubscriber, subSession));
		Thread senderThread = new Thread(new SenderTask(topicPublisher, pubSession));
		receiverThread.start();
		senderThread.start();
	}

	public static class ReceiverTask implements Runnable {
		TopicSubscriber topicSubscriber = null;
		TopicSession session;

		public ReceiverTask(TopicSubscriber topicSubscriber, TopicSession subSession) {
			super();
			this.topicSubscriber = topicSubscriber;
			this.session = subSession;
		}

		@Override
		public void run() {
			Message message;
			while (true) {
				try {
					message = topicSubscriber.receive();
					if (message instanceof TextMessage) {
						TextMessage text = (TextMessage) message;
						System.out.println("Received: " + text.getText());
					} else if (message != null) {
						System.out.println("Received non text message");
					}
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class SenderTask implements Runnable {
		TopicPublisher topicPublisher;
		TopicSession session;

		public SenderTask(TopicPublisher topicPublisher, TopicSession session) {
			super();
			this.topicPublisher = topicPublisher;
			this.session = session;
		}

		@Override
		public void run() {
			BufferedReader waiter = null;
			waiter = new BufferedReader(new InputStreamReader(System.in));
			try {
				String str = waiter.readLine();
				while (!"quit".equalsIgnoreCase(str)) {
					TextMessage textMessage = session.createTextMessage(str);
					topicPublisher.publish(textMessage);
					str = waiter.readLine();
				}
				session.close();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}
