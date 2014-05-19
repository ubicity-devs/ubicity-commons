package at.ac.ait.ubicity.commons.broker.events;

import java.util.HashMap;

public interface Metadata {

	/**
	 * Returns the Ubicity Consumer name for processing.
	 * 
	 * @return
	 */
	String getDestination();

	/**
	 * Returns the action for the consumer AddOn.
	 * 
	 * @return
	 */
	String getAction();

	/**
	 * Returns the number when this entry is processed
	 * 
	 * @return
	 */
	int getSequence();

	/**
	 * Returns the number when this entry is processed
	 * 
	 * @return
	 */
	HashMap<String, String> getProperties();
}
