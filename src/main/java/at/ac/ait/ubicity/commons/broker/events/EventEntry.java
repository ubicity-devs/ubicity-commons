package at.ac.ait.ubicity.commons.broker.events;

import java.io.Serializable;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class EventEntry implements Serializable {
	private static final long serialVersionUID = -5123460570794468542L;

	private static Gson gson = new GsonBuilder().create();

	public enum Property {
		ID, ES_INDEX, ES_TYPE, CB_BUCKET
	}

	private HashMap<Property, String> header = new HashMap<Property, String>();
	private final String body;

	public EventEntry(HashMap<Property, String> header, String body) {
		this.header = header;
		this.body = body;
	}

	public EventEntry(String jsonString) {
		EventEntry e = gson.fromJson(jsonString, this.getClass());
		this.header = e.header;
		this.body = e.body;
	}

	public HashMap<Property, String> getHeader() {
		return this.header;
	}

	public String getBody() {
		return this.body;
	}

	public String toJson() {
		return gson.toJson(this);
	}
}
