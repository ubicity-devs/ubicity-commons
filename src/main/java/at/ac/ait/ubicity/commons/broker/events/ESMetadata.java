package at.ac.ait.ubicity.commons.broker.events;

import java.util.HashMap;
import java.util.Map.Entry;

public class ESMetadata implements Metadata {

	public enum Properties {
		ES_INDEX, ES_TYPE
	}

	public enum Action {
		INDEX, UPDATE, DELETE
	}

	private final static String destination = "elasticsearch";
	private final String action;
	private final int sequence;
	private final HashMap<String, String> properties = new HashMap<String, String>();

	public ESMetadata(Action action, int sequence,
			HashMap<Properties, String> props) {
		this.action = action.toString();
		this.sequence = sequence;

		if (props != null) {
			for (Entry<Properties, String> e : props.entrySet()) {
				this.properties.put(e.getKey().toString(), e.getValue());
			}
		}
	}

	@Override
	public String getDestination() {
		return destination;
	}

	@Override
	public String getAction() {
		return action;
	}

	@Override
	public int getSequence() {
		return sequence;
	}

	@Override
	public HashMap<String, String> getProperties() {
		return properties;
	}
}
