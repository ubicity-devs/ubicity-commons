package at.ac.ait.ubicity.commons.broker.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EventEntry {

	private String id;
	private String data;
	private int curSequence = 0;

	protected boolean poisoned;

	private long createdTs;

	private List<Metadata> metadata;

	public EventEntry() {
		this.createdTs = System.currentTimeMillis();
	}

	public EventEntry(String id, List<Metadata> metadata, String data) {
		this();
		this.id = id;
		this.data = data;
		this.metadata = metadata;
	}

	public EventEntry(String id, Metadata metadata, String data) {
		this(id, Arrays.asList(metadata), data);
	}

	public String getId() {
		return this.id;
	}

	public String getData() {
		return this.data;
	}

	public void incSequence() {
		curSequence++;
	}

	public List<Metadata> getCurrentMetadata() {

		List<Metadata> curMeta = new ArrayList<Metadata>();

		for (int i = 0; i < metadata.size(); i++) {
			if (metadata.get(i).getSequence() == curSequence) {
				curMeta.add(metadata.get(i));
			}
		}
		return curMeta;
	}

	public void copy(EventEntry entry) {
		this.id = entry.id;
		this.poisoned = entry.poisoned;
		this.curSequence = entry.curSequence;
		this.data = entry.data;
		this.metadata = entry.metadata;
		this.createdTs = entry.createdTs;
	}

	public boolean isPoisoned() {
		return poisoned;
	}

	public long getCreatedTs() {
		return this.createdTs;
	}
}
