package at.ac.ait.ubicity.commons.broker;

import java.util.HashMap;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.fusesource.stomp.jms.StompJmsDestination;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.exceptions.UbicityBrokerException;

public abstract class BrokerProducer extends AbstractBrokerClient {

	private static final Logger logger = Logger.getLogger(BrokerProducer.class);
	private static HashMap<String, MessageProducer> producers = new HashMap<String, MessageProducer>();

	public synchronized void publish(EventEntry msg) throws UbicityBrokerException {

		MessageProducer producer = getProducer(msg.getNextPlugin());

		try {
			TextMessage tMsg = getSession().createTextMessage();
			tMsg.setText(msg.toJson());
			producer.send(tMsg);
		} catch (JMSException e) {
			throw new UbicityBrokerException(e);
		}
	}

	/**
	 * Returns the Messageproducer for the given destination.
	 * 
	 * @param dest
	 * @return
	 * @throws UbicityBrokerException
	 */
	private synchronized MessageProducer getProducer(String dest) throws UbicityBrokerException {

		MessageProducer producer = producers.get(dest);

		if (producer == null) {
			Destination destination;
			try {
				destination = StompJmsDestination.createDestination(getConnection(), calcDestination(dest));
				producer = getSession().createProducer(destination);
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
				producers.put(dest, producer);
			} catch (JMSException e) {
				throw new UbicityBrokerException(e);
			}
		}

		return producer;
	}

	public void shutdown(String dest) {
		try {
			MessageProducer producer = producers.get(dest);

			if (producer != null) {
				producer.close();
			}
			// producer.close();
		} catch (JMSException e) {
			logger.warn("Close producer threw exc.", e);
		}
		super.shutdown();
	}
}
