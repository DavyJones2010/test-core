package edu.xmu.test.j2ee.jms.openjms;

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

/**
 * <code>Subscriber</code> example.
 *
 */
public class Subscriber {

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
		TopicSubscriber topicSubscriber = null;
		int msgCount = 0;
		if (args.length != 2) {
			System.out.println("usage: Subscriber <topic> <msgCount>");
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
			topicSubscriber = session.createSubscriber(topic);

			// start the connection
			connection.start();

			for (int i = 0; i < msgCount; i++) {
				Message message = topicSubscriber.receive();
				if (message instanceof TextMessage) {
					TextMessage text = (TextMessage) message;
					System.out.println("Received: " + text.getText());
				} else if (message != null) {
					System.out.println("Received non text message");
				}
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
