package eu.openminted.messageservice.connector;

import javax.jms.Message;

/**
 * @author ilsp
 *
 */
public interface MessagesHandler {

	public void handleMessage(Message msg);
}
