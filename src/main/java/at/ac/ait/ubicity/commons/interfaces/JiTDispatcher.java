package at.ac.ait.ubicity.commons.interfaces;

import at.ac.ait.ubicity.commons.jit.Action;
import at.ac.ait.ubicity.commons.jit.Answer;

public interface JiTDispatcher {
	/**
	 * Distributes the Action to the given JiT Plugin.
	 * 
	 * @return
	 */
	Answer distribute(Action action);
}
