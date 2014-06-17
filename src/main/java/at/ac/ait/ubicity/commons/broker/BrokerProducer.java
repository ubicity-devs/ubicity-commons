package at.ac.ait.ubicity.commons.broker;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.fusesource.stomp.jms.StompJmsDestination;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;

public abstract class BrokerProducer extends AbstractBrokerClient {

	private static final Logger logger = Logger.getLogger(BrokerProducer.class);

	private MessageProducer producer;

	/**
	 * Creates the producer for the given destination.
	 * 
	 * @param dest
	 * @throws UbicityBrokerException
	 */
	protected void setProducer(String dest) throws UbicityBrokerException {

		Destination destination;
		try {
			destination = StompJmsDestination.createDestination(
					getConnection(), calcDestination(dest));
			producer = getSession().createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		} catch (JMSException e) {
			throw new UbicityBrokerException(e);
		}
	}

	public void publish(EventEntry msg) throws UbicityBrokerException {

		try {
			TextMessage tMsg = getSession().createTextMessage();
			tMsg.setText(msg.toJson());
			producer.send(tMsg);
		} catch (JMSException e) {
			throw new UbicityBrokerException(e);
		}
	}

	@Override
	public void shutdown() {
		try {
			producer.close();
		} catch (JMSException e) {
			logger.warn("Close producer threw exc.", e);
		}
		super.shutdown();
	}
}
