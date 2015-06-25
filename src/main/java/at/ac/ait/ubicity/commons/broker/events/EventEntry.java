package at.ac.ait.ubicity.commons.broker.events;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import at.ac.ait.ubicity.commons.exceptions.UbicityException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventEntry implements Serializable {
	private static final long serialVersionUID = -5123460570794468542L;

	private static Gson gson = new GsonBuilder().create();

	public enum Property {
		ID, PLUGIN_CHAIN, // manatory broker properties
		ES_INDEX, ES_TYPE, // Elasticsearch properties
		CB_BUCKET // Couchbase properties
	}

	private HashMap<Property, String> header = new HashMap<Property, String>();
	/**
	 * List of destinations for processing in successive order.
	 */
	private transient List<String> pluginChain = new ArrayList<String>();
	private String body;

	public EventEntry(HashMap<Property, String> header, String body) throws UbicityException {
		init(header, body);
	}

	public EventEntry(String jsonString) throws UbicityException {
		EventEntry e = gson.fromJson(jsonString, this.getClass());
		init(e.header, e.body);
	}

	@SuppressWarnings("unchecked")
	private void init(HashMap<Property, String> header, String body) {
		this.header = header;
		this.body = body;

		checkManatoryFields();
		this.pluginChain = gson.fromJson(getProperty(Property.PLUGIN_CHAIN), List.class);
	}

	/**
	 * Check if all manatory fields are set. Else throw {@code UbicityException}.
	 * 
	 * @throws UbicityException
	 */
	private void checkManatoryFields() throws UbicityException {
		if (getProperty(Property.ID) == null) {
			throw new UbicityException("Property.ID must not be null");
		}

		if (getProperty(Property.PLUGIN_CHAIN) == null) {
			throw new UbicityException("Property.PLUGIN_CHAIN must not be null");
		}
	}

	HashMap<Property, String> getHeader() {
		return this.header;
	}

	public static String formatPluginChain(List<String> pluginChain) {
		return gson.toJson(pluginChain);
	}

	public String getProperty(Property prop) {
		return this.header.get(prop);
	}

	public String getBody() {
		return this.body;
	}

	public String toJson() {
		return gson.toJson(this);
	}

	public String getNextPlugin() {
		return pluginChain.get(0);
	}

	List<String> getPluginChain() {
		return pluginChain;
	}
}
