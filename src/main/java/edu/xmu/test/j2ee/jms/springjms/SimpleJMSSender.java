package edu.xmu.test.j2ee.jms.springjms;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

public class SimpleJMSSender {
	/**
	 * <ol>
	 * <li>了解如何在spring中配置jndi: 1.如何创建jndicontext 2.如何lookup对应的object
	 * <li>了解spring对jms的支持类JmsTemplate的使用
	 * </ol>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		//ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext(
		//		"spring/jms/spring-jms-openjms.xml");
		//JmsTemplate jmsTemplate = (JmsTemplate) classPathXmlApplicationContext.getBean("jmsQueueTemplate");
		//jmsTemplate.convertAndSend("This is a msg");
		//classPathXmlApplicationContext.close();
	}
}
