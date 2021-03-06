package eu.openminted.messageservice.connector;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
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

	private MessagesHandler messagesHandler;
	
	private String messagesHost;
	private TopicConnection connection = null;
	private Session session;
	
	public MessageServiceSubscriber(String messagesHost) {
		this.messagesHost = messagesHost;

		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(messagesHost);
		try {
			connection = connectionFactory.createTopicConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
		} catch (JMSException e) {
			log.info("error");
		}
		
	}

	public void addTopic(String topic){
		try {
			Destination targetTopic = session.createTopic(topic);
			MessageConsumer responseConsumer = session.createConsumer(targetTopic);
			responseConsumer.setMessageListener(this);		
		} catch (JMSException e) {
			log.info("error");
		}

	}
	
	public MessagesHandler getMessagesHandler() {
		return messagesHandler;
	}

	public void setMessagesHandler(MessagesHandler messagesHandler) {
		this.messagesHandler = messagesHandler;
	}

	@Override
	public void onMessage(Message msg) {
        try {
        	log.info("onMessage:" + msg.getJMSType() + " " + msg.getJMSMessageID());
        	log.info("onMessage:" + msg.toString());
	        if (msg instanceof TextMessage) {
	        	TextMessage textMessage = (TextMessage) msg;
	        	log.info("text message:" + textMessage.getText());
	        }
	        
        	if(messagesHandler!=null){
        		messagesHandler.handleMessage(msg);
        	}
        	
        }catch(JMSException e){
        	log.info("error on receiving message");
        	// Handle the exception appropriately
        }

	}

	public void close() throws JMSException {
		session.close();
		if (connection != null) {
			connection.close();
		}				
	}
}
