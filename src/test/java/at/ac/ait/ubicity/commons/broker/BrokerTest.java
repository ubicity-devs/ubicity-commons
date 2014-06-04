package at.ac.ait.ubicity.commons.broker;

import java.util.HashMap;

import org.junit.Ignore;
import org.junit.Test;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.events.EventEntry.Property;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;

import com.google.gson.Gson;

public class BrokerTest {

	@Ignore
	@Test
	public void testProducer() throws UbicityBrokerException {

		SimpleProducer prod = new SimpleProducer();
		prod.publish();

	}

	@Ignore
	@Test
	public void testJson() {

		Gson gson = new Gson();

		HashMap<Property, String> header = new HashMap<EventEntry.Property, String>();
		header.put(Property.ES_INDEX, "index");
		header.put(Property.ES_TYPE, "type");

		EventEntry e = new EventEntry(header, "Body");

		String json = gson.toJson(e);

		System.out.println(json);

		EventEntry e1 = gson.fromJson(json, EventEntry.class);

		System.out.println(e1.getHeader().get(Property.ES_TYPE));
	}
}
