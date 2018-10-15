package edu.xmu.test.j2ee.jms.openjms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QLender {
	public static void main(String[] args) throws IOException {

		Context context = null;
		ConnectionFactory factory = null;
		Connection connection = null;
		String factoryName = "ConnectionFactory";
		String reqDestName = "LoanRequestQ";
		String respDestName = "LoanResponseQ";
		Destination reqDest = null;
		Destination respDest = null;
		Session session = null;
		MessageProducer sender = null;
		MessageConsumer consumer = null;
		BufferedReader waiter = null;
		try {
			// create the JNDI initial context.
			context = new InitialContext();

			// look up the ConnectionFactory
			factory = (ConnectionFactory) context.lookup(factoryName);

			// look up the Destination
			reqDest = (Destination) context.lookup(reqDestName);
			respDest = (Destination) context.lookup(respDestName);

			// create the connection
			connection = factory.createConnection();

			// create the session
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			// create the sender
			consumer = session.createConsumer(reqDest);
			sender = session.createProducer(respDest);

			// start the connection, to enable message sends
			connection.start();
			String respMsg = null;
			while (!"quit".equalsIgnoreCase(respMsg)) {
				Message resp = consumer.receive();
				System.out.println("Received: " + ((TextMessage) resp).getText());

				TextMessage message = session.createTextMessage();
				waiter = new BufferedReader(new InputStreamReader(System.in));
				respMsg = waiter.readLine();
				message.setText(respMsg);
				message.setJMSCorrelationID(resp.getJMSMessageID());
				sender.send(message);
				System.out.println("Sent: " + message.getText());
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
