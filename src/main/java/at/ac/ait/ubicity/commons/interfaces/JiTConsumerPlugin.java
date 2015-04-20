package at.ac.ait.ubicity.commons.interfaces;

import at.ac.ait.ubicity.commons.jit.Action;
import at.ac.ait.ubicity.commons.jit.Answer;

public interface JiTConsumerPlugin extends UbicityPlugin {

	/**
	 * Execute received action.
	 * 
	 * @param action
	 * @return {Answer}
	 */
	public Answer process(Action action);
}
