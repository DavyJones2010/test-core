package edu.xmu.test.j2ee.jms.openjms.sample;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ChatReceiver {
	public static void main(String[] args) throws NamingException, JMSException {
		Context context = null;
		TopicConnectionFactory factory = null;
		TopicConnection connection = null;
		TopicSession session = null;
		Topic topic = null;
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
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		topicSubscriber = session.createSubscriber(topic);
		connection.start();

		Thread receiverThread = new Thread(new ReceiverTask(topicSubscriber));
		receiverThread.start();
	}

	public static class ReceiverTask implements Runnable {
		TopicSubscriber topicSubscriber = null;

		public ReceiverTask(TopicSubscriber topicSubscriber) {
			super();
			this.topicSubscriber = topicSubscriber;
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
}
