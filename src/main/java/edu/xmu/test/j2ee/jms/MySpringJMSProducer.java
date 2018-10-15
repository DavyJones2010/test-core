package edu.xmu.test.j2ee.jms;

import javax.annotation.PostConstruct;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

//@ComponentScan
public class MySpringJMSProducer {
	private static final Logger logger = Logger.getLogger(MySpringJMSProducer.class);
	@Autowired
	private JmsTemplate template = null;
	private int messageCount = 100;

	/**
	 * Generates JMS messages
	 */
	@PostConstruct
	public void generateMessages() throws JMSException, NamingException {
		for (int i = 0; i < messageCount; i++) {
			final int index = i;
			final String text = "Message number is " + i + ".";
			InitialContext context = new InitialContext();
			QueueConnectionFactory f = (QueueConnectionFactory) context.lookup("myQueueConnectionFactory");
			template.setConnectionFactory(f);
			//template.send(new MessageCreator() {
			//	public Message createMessage(Session session) throws JMSException {
			//		TextMessage message = session.createTextMessage(text);
			//		message.setIntProperty("messageCount", index);
			//		logger.info("Sending message: " + text);
			//		return message;
			//	}
			//});
		}
	}
}
