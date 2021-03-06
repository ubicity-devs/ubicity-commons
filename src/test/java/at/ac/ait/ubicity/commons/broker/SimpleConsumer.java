package at.ac.ait.ubicity.commons.broker;

import org.apache.log4j.Logger;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.exceptions.UbicityBrokerException;

public class SimpleConsumer extends BrokerConsumer {

	final static Logger logger = Logger.getLogger(SimpleConsumer.class);

	public SimpleConsumer() throws UbicityBrokerException {
		super.init();
		setConsumer(this, "/queue/ES.**");
	}

	@Override
	protected void onReceived(String destination, EventEntry msg) {
		logger.info("Received from '" + destination + "': " + msg.getBody());
	}

	@Override
	protected void onReceivedRaw(String destination, String tmsg) {
		// Not used here
	}

	/**
	 * Mainly here for debugging purposes.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public final static void main(String[] args) throws Exception {
		SimpleConsumer c = new SimpleConsumer();

		while (true)
			Thread.sleep(100);
	}

}
