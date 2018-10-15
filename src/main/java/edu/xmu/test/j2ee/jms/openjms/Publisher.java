package edu.xmu.test.j2ee.jms.openjms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * <code>Publisher</code> example.
 *
 */
public class Publisher {

	/**
	 * Main line.
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Context context = null;
		TopicConnectionFactory factory = null;
		TopicConnection connection = null;
		Topic topic = null;
		String factoryName = "ConnectionFactory";
		String topicName = null;
		TopicSession session = null;
		TopicPublisher topicPublisher = null;
		int msgCount = 0;
		if (args.length != 2) {
			System.out.println("usage: Publisher <topic> <msgCount>");
			System.exit(1);
		}

		topicName = args[0];
		msgCount = Integer.parseInt(args[1]);

		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (TopicConnectionFactory) context.lookup(factoryName);
			topic = (Topic) context.lookup(topicName);

			// create the connection
			connection = factory.createTopicConnection();

			// create the session
			session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topicPublisher = session.createPublisher(topic);

			// start the connection
			connection.start();

			for (int i = 0; i < msgCount; i++) {
				TextMessage textMessage = session.createTextMessage("hello " + i);
				topicPublisher.publish(textMessage);
				System.out.println("published msg: hello " + i);
			}
		} catch (JMSException exception) {
			exception.printStackTrace();
		} catch (NamingException exception) {
			exception.printStackTrace();
		} finally {
			// close the context
			if (context != null) {
				try {
					context.close();
				} catch (NamingException exception) {
					exception.printStackTrace();
				}
			}

			// close the connection
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}
