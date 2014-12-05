package at.ac.ait.ubicity.commons.broker;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.fusesource.stomp.jms.StompJmsDestination;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;

public abstract class BrokerConsumer extends AbstractBrokerClient implements
		MessageListener {

	private static final Logger logger = Logger.getLogger(BrokerConsumer.class);

	protected boolean receiveRaw = false;

	/**
	 * Message entry is converted to EventEntry class.
	 * 
	 * @param destination
	 * @param msg
	 */
	protected abstract void onReceived(String destination, EventEntry msg);

	/**
	 * Message entry is returned in raw format without modification.
	 * 
	 * @param destination
	 * @param tmsg
	 */
	protected abstract void onReceivedRaw(String destination, String tmsg);

	@Override
	public void onMessage(Message message) {
		if (TextMessage.class.isInstance(message)) {
			TextMessage tMsg = (TextMessage) message;
			try {
				String dest = message.getJMSDestination().toString();

				if (receiveRaw) {
					onReceivedRaw(dest, tMsg.getText());
				} else {
					onReceived(dest, new EventEntry(tMsg.getText()));
				}

			} catch (JMSException e) {
				logger.warn("on Message caught exc.", e);
			}
		} else {
			logger.warn("Ignored message of type "
					+ message.getClass().getSimpleName());
		}
	}

	/**
	 * Sets the messagelistener and the given destination
	 * (topic/queue/wildcard/...)
	 * 
	 * @param listener
	 * @param dest
	 * @throws UbicityBrokerException
	 * @throws JMSException
	 */
	protected void setConsumer(MessageListener listener, String dest)
			throws UbicityBrokerException {

		try {
			Destination destination = StompJmsDestination.createDestination(
					getConnection(), calcDestination(dest));
			MessageConsumer consumer = getSession().createConsumer(destination);

			consumer.setMessageListener(listener);
		} catch (JMSException e) {
			throw new UbicityBrokerException(e);
		}
	}
}