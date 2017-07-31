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

import eu.openminted.messageservice.messages.GSON;

/**
 * @author galanisd
 *
 */
public class MessageServicePublisher {

	private static final Logger log = LoggerFactory.getLogger(MessageServicePublisher.class);

	private String messagesHost;
	private TopicConnection connection = null;
	private TopicSession session = null;

	private GSON gson;

	public MessageServicePublisher(String messagesHost) {
		this.messagesHost = messagesHost;
		gson = new GSON();
	}

	private void init() throws JMSException{
		TopicConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messagesHost);
		connection = connectionFactory.createTopicConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);	
		connection.start();
	}
	
	public void createTopic(String topicName) throws JMSException {
		init();
		// Create topic
		session.createTopic(topicName);
		close();
	}

	public void publishMessage(String topicName, String message) throws JMSException {
		publishMessage(topicName, message);
	}

	public void publishMessage(String topicName, Object message) throws JMSException {
		init();
		// Create topic and publish a message.
		Topic topic = session.createTopic(topicName);
		TopicPublisher topicPublisher = session.createPublisher(topic);
		TextMessage textMsg = session.createTextMessage(gson.getJSON(message));
		
		topicPublisher.publish(textMsg);
		topicPublisher.close();
		
		close();
	}

	
	public void close() throws JMSException {
		session.close();
		if (connection != null) {
			connection.close();
		}				
	}
}
