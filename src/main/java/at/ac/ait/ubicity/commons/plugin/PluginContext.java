/**
    Copyright (C) 2013  AIT / Austrian Institute of Technology
    http://www.ait.ac.at

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */

package at.ac.ait.ubicity.commons.plugin;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.json.JSONObject;

import at.ac.ait.ubicity.commons.ConsumerPoison;
import at.ac.ait.ubicity.commons.ESClient;
import at.ac.ait.ubicity.commons.JSONConsumer;
import at.ac.ait.ubicity.commons.JSONProducer;
import at.ac.ait.ubicity.commons.interfaces.UbicityPlugin;

/**
 *
 * @author Jan van Oort
 */
public final class PluginContext {

	protected final UbicityPlugin plugin;
	protected BlockingQueue<JSONObject> queue;
	protected final JSONConsumer associatedConsumer;

	private final ESClient client;

	// making this field publicly visible is against all recommendations, and
	// considered Bad Practice;
	// the <public> modifier is here as a hack, for performance reasons
	protected final JSONProducer associatedProducer;
	protected final Thread consumerThread;
	protected final Thread producerThread;

	private int TERMINATION_DELAY;

	private static final Logger logger = Logger.getLogger(PluginContext.class
			.getName());

	public PluginContext(ESClient _client, UbicityPlugin _plugin) {
		// we do not keep a reference to the client;
		plugin = _plugin;
		client = _client;

		// get our own Configuration
		try {
			// set necessary stuff for us to ueberhaupt be able to work
			Configuration config = new PropertiesConfiguration("commons.cfg");

			queue = new ArrayBlockingQueue<JSONObject>(
					config.getInt("commons.lmax.queue_size"));
			TERMINATION_DELAY = config
					.getInt("commons.plugins.graceful_termination_delay");

		} catch (ConfigurationException noConfig) {
			// log this problem and then go along with default configuration
			logger.warning("Core :: could not configure from core.cfg file [ not found, or there was a problem with it ], trying to revert to DefaultConfiguration  : "
					+ noConfig.toString());
		}

		// create a JSONConsumer for this queue
		associatedConsumer = new JSONConsumer(_plugin, _client, queue);
		// we also need a JSONProducer for this queue; in and by itself it
		// produces nothing, this is done by the plugin;
		// the JSONProducer simply acts as a producer towards our queue, and
		// contributes to fully hiding the queue from the plugin's visibility.
		associatedProducer = new JSONProducer(queue);
		consumerThread = new Thread(associatedConsumer);
		producerThread = new Thread(getAssociatedProducer());
		// the consumer thread gets a very low priority, as we want to schedule
		// in favour of the producer thread
		consumerThread.setPriority(Thread.MIN_PRIORITY + 1);
		// thread names come in handy for debugging purposes
		consumerThread.setName("JSONConsumer for " + _plugin.getName());
		producerThread.setPriority(Thread.MAX_PRIORITY - 1);
		producerThread.setName("JSONProducer for " + _plugin.getName());
		// we're done, start the show !
		consumerThread.start();
		producerThread.start();
	}

	public void destroy() {

		plugin.mustStop();
		try {
			consumerThread.join(TERMINATION_DELAY);
		} catch (InterruptedException _interrupt) {
			Thread.interrupted();
		} finally {
			try {
				producerThread.stop();
			} catch (Error ee) {
				logger.severe("PluginContext.destroy() for "
						+ this.plugin.getName()
						+ " : caught an Error while attemptiong destruction :: "
						+ ee.toString());
			} catch (RuntimeException prettyBad) {
				logger.severe("PluginContext.destroy() for "
						+ this.plugin.getName()
						+ " : caught an runtime exception while attemptiong destruction :: "
						+ prettyBad.toString());
			} catch (Throwable e) {
				logger.severe("PluginContext.destroy() for "
						+ this.plugin.getName()
						+ " : caught an unspecified problem [ Throwable ] while attemptiong destruction :: "
						+ e.toString());
			} finally {
				consumerThread.stop();
			}
		}
		logger.info("plugin " + plugin.getName() + " destroyed");
	}

	public boolean prepareDestroy() {
		try {
			// let the the consumer be poisoned; delegate this task to a
			// dedicated thread, and carry on
			ConsumerPoison poison = new ConsumerPoison();
			PluginTerminator terminator = new PluginTerminator(poison, queue);
			Thread tTerminator = new Thread(terminator);
			tTerminator.setName("Terminator for " + plugin.getName());
			tTerminator.start();
			return true;
			// now, life carries on as usual; at some time in the future, the
			// plugin's consumer is going to be killed off,
			// although only after having processed all the valid json objects
			// in its queue;
			// the consumer - before dying - will then notify the Core, which
			// will call destroy() upon us,
			// and we do the cleaning work before being removed from the Core
			// and being <null>-ed ourselves.
		} catch (Throwable t) {
			logger.severe("some problem arose while preparing destruction for plugin "
					+ plugin.getName() + ": " + t.toString());
			return false;
		}
	}

	/**
	 * @return the associatedProducer
	 */
	public JSONProducer getAssociatedProducer() {
		return associatedProducer;
	}

	public ESClient getESClient() {
		return client;
	}

	public UbicityPlugin getUbicityPlugin() {
		return plugin;
	}
}