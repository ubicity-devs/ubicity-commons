package at.ac.ait.ubicity.commons.broker;

import java.util.HashMap;
import java.util.UUID;

import at.ac.ait.ubicity.commons.broker.BrokerProducer;
import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.events.EventEntry.Property;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;

public class SimpleProducer extends BrokerProducer {

	public SimpleProducer() throws UbicityBrokerException {
		super.init("", "");
		setProducer("/queue/ES.TEST");
	}

	public void publish() throws UbicityBrokerException {

		for (int i = 0; i < 10; i++) {
			publish(createEvent("data" + i));
		}

	}

	private EventEntry createEvent(String data) {

		HashMap<Property, String> header = new HashMap<Property, String>();
		header.put(Property.ES_INDEX, "TEST");
		header.put(Property.ES_TYPE, "test");
		header.put(Property.ID, UUID.randomUUID().toString());

		return new EventEntry(header, data);
	}

}
