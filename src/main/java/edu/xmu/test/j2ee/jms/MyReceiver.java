package edu.xmu.test.j2ee.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

/**
 * Tutorials from {@link <a href="http://www.javatpoint.com/jms-tutorial">JMS Tutorial</a>}
 */
public class MyReceiver {
	public static void main(String[] args) throws Exception {
		// 1) Create and start connection
		InitialContext ctx = new InitialContext();
		QueueConnectionFactory f = (QueueConnectionFactory) ctx.lookup("myQueueConnectionFactory");
		QueueConnection con = f.createQueueConnection();
		con.start();
		// 2) create Queue session
		QueueSession ses = con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		// 3) get the Queue object
		Queue t = (Queue) ctx.lookup("myQueue");
		// 4)create QueueReceiver
		QueueReceiver receiver = ses.createReceiver(t);

		// 5) create listener object
		MyListener listener = new MyListener();

		// 6) register the listener object with receiver
		receiver.setMessageListener(listener);

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
