package at.ac.ait.ubicity.commons.interfaces;

import net.xeoh.plugins.base.Plugin;

public interface UbicityPlugin extends Plugin {
	/**
	 * Returns the name of the plugin
	 * 
	 * @return
	 */
	String getName();

	/**
	 * Initialization of the Plugin on startup
	 */
	void init();

	/**
	 * Method called when shutting down the plugin
	 */
	void shutdown();
}
