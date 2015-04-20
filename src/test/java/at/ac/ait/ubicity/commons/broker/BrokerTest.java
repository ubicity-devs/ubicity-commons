package at.ac.ait.ubicity.commons.broker;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.events.EventEntry.Property;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BrokerTest {

	private static Gson gson = new GsonBuilder().create();

	@Test
	public void testJson() {

		HashMap<Property, String> header = new HashMap<EventEntry.Property, String>();
		header.put(Property.ID, "546546");
		header.put(Property.PLUGIN_CHAIN, EventEntry.formatPluginChain(Arrays.asList("/queue/ES.RSS")));
		header.put(Property.ES_INDEX, "index");
		header.put(Property.ES_TYPE, "type");

		EventEntry e = new EventEntry(header, "Body");

		String json = gson.toJson(e);

		System.out.println(json);

		EventEntry e1 = gson.fromJson(json, EventEntry.class);

		System.out.println(e1.getProperty(Property.ES_TYPE));

		System.out.println(e1.getNextPlugin());
	}
}
