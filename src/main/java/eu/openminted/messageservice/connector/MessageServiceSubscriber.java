package eu.openminted.messageservice.connector;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TopicConnection;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ilsp
 *
 */
public class MessageServiceSubscriber implements MessageListener {

	private static final Logger log = LoggerFactory.getLogger(MessageServiceSubscriber.class);

	private String messagesHost;
	private TopicConnection connection = null;

	public MessageServiceSubscriber(String messagesHost) {
		this.messagesHost = messagesHost;

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messagesHost);
		try {
			connection = connectionFactory.createTopicConnection();
			connection.start();

			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			Destination dest = session.createTopic(TopicsRegistry.workflowsExecution);
			MessageConsumer responseConsumer = session.createConsumer(dest);

			// This class will handle the messages to the temp queue as well
			responseConsumer.setMessageListener(this);
		} catch (JMSException e) {
			log.info("error");
		}

	}

	@Override
	public void onMessage(Message msg) {
        try {
        	log.info("onMessage:" + msg.getJMSType() + " " + msg.getJMSMessageID());
        	log.info("onMessage:" + msg.toString());
	        // if (message instanceof TextMessage) {
	        // TextMessage textMessage = (TextMessage) message;
	        // messageText = textMessage.getText();
	        // System.out.println("messageText = " + messageText);
        	
        }catch(JMSException e){
        	log.info("error on receiving message");
        	// Handle the exception appropriately
        }

}}
