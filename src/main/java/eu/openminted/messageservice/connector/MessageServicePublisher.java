package eu.openminted.messageservice.connector;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author galanisd
 *
 */
public class MessageServicePublisher {

	private static final Logger log = LoggerFactory.getLogger(MessageServicePublisher.class);

	private String messagesHost;
	private TopicConnection connection = null;
	private TopicSession session = null;


	public MessageServicePublisher(String messagesHost) {
		this.messagesHost = messagesHost;
	}

	public void createTopic(String topicName) throws JMSException {
		TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messagesHost);
		connection = connectionFactory.createTopicConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		connection.start();
		session.createTopic(topicName);
		session.close();

		if (connection != null) {
			connection.close();
		}
	}

	public void publishMessage(String topicName, String message) throws JMSException {

		TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messagesHost);
		connection = connectionFactory.createTopicConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

		connection.start();
		Topic topic = session.createTopic(topicName);

		TopicPublisher send = session.createPublisher(topic);
		TextMessage tm = session.createTextMessage(message);
		send.publish(tm);
		send.close();

	}
}
