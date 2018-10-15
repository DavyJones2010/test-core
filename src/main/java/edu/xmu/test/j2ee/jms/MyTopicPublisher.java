package edu.xmu.test.j2ee.jms;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

/**
 * Tutorials from {@link <a href="http://www.javatpoint.com/jms-tutorial">JMS Tutorial</a>}
 */
public class MyTopicPublisher {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		TopicConnectionFactory f = (TopicConnectionFactory) context.lookup("myTopicConnectionFactory");
		TopicConnection connection = f.createTopicConnection();
		connection.start();
		// 2) create queue session
		TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		// 3) get the Queue object
		Topic topic = (Topic) context.lookup("myTopicQueue");
		// 4) create QueueSender object
		TopicPublisher publisher = session.createPublisher(topic);
		// 5) create TextMessage object
		TextMessage textMessage = session.createTextMessage();
		// 6) write message
		textMessage.setText("Hello world");
		// 7) send message
		publisher.publish(textMessage);
		// 8) connection close
		connection.close();
		System.exit(0);
	}
}
