package at.ac.ait.ubicity.commons.broker;

import at.ac.ait.ubicity.commons.interfaces.JiTDispatcher;
import at.ac.ait.ubicity.commons.jit.Action;
import at.ac.ait.ubicity.commons.jit.Answer;

public class JiTBroker {
	private JiTDispatcher dispatcher;

	private static JiTBroker instance = null;

	protected JiTBroker() {
		// hide default constructor
	}

	protected JiTBroker(JiTDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public static void registerDispatcher(JiTDispatcher dispatcher) {
		if (instance == null) {
			instance = new JiTBroker(dispatcher);
		}
	}

	/**
	 * Execute received action.
	 * 
	 * @param action
	 * @return {Answer}
	 */
	public static Answer process(Action action) {
		return instance.dispatcher.distribute(action);
	}
}