package edu.xmu.test.j2ee.jms;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Tutorials from {@link <a href="http://www.javatpoint.com/jms-tutorial">JMS Tutorial</a>}
 */
public class MySender {
	public static void main(String[] args) throws Exception {
		InitialContext context = new InitialContext();
		QueueConnectionFactory f = (QueueConnectionFactory) context.lookup("myQueueConnectionFactory");
		QueueConnection connection = f.createQueueConnection();
		connection.start();
		// 2) create queue session
		QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		// 3) get the Queue object
		Queue queue = (Queue) context.lookup("myQueue");
		// 4) create QueueSender object
		QueueSender sender = session.createSender(queue);
		// 5) create TextMessage object
		TextMessage textMessage = session.createTextMessage();

		// 6) write message
		textMessage.setText("Hello world");
		// 7) send message
		sender.send(textMessage);
		// 8) connection close
		connection.close();
		System.exit(0);
	}
}
