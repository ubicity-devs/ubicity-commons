package at.ac.ait.ubicity.commons.templates;

import java.util.HashMap;

public abstract class AbstractAnalyticsDTO extends AbstractDTO {

	private final HashMap<String, Object> processed = new HashMap<String, Object>();

	public HashMap<String, Object> getProcessed() {
		return processed;
	}

	/**
	 * Get processing result by key.
	 * 
	 * @param key
	 * @return Object
	 */
	public Object getAnalyticsResult(String key) {
		return processed.get(key);
	}

	/**
	 * Set analytics result for given key.
	 * 
	 * @param key
	 * @param obj
	 */
	public void setAnalyticsResult(String key, Object obj) {
		processed.put(key, obj);
	}
}
