package edu.xmu.test.j2ee.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

/**
 * Tutorials from {@link <a href="http://www.javatpoint.com/jms-tutorial">JMS Tutorial</a>}
 */
public class MyTopicSubscriber {
	public static void main(String[] args) throws Exception {
		// 1) Create and start connection
		InitialContext ctx = new InitialContext();
		TopicConnectionFactory f = (TopicConnectionFactory) ctx.lookup("myTopicConnectionFactory");
		TopicConnection con = f.createTopicConnection();
		con.start();
		// 2) create queue session
		TopicSession ses = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		// 3) get the Queue object
		Topic t = (Topic) ctx.lookup("myTopicQueue");
		// 4)create QueueReceiver
		TopicSubscriber subscriber = ses.createSubscriber(t);
		// 5) create listener object
		MyListener listener = new MyListener();
		// 6) register the listener object with receiver
		subscriber.setMessageListener(listener);
		System.out.println("Receiver1 is ready, waiting for messages...");
		System.out.println("press Ctrl+c to shutdown...");
		while (true) {
			Thread.sleep(1000);
		}
	}

	static class MyListener implements MessageListener {

		@Override
		public void onMessage(Message message) {
			TextMessage msg = (TextMessage) message;
			try {
				System.out.println("following message is received: " + msg.getText());
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}

	}
}
