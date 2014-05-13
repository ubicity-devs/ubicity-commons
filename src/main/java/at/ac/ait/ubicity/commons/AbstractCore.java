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

package at.ac.ait.ubicity.commons;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONObject;

import at.ac.ait.ubicity.commons.interfaces.UbicityAddOn;
import at.ac.ait.ubicity.commons.interfaces.UbicityPlugin;
import at.ac.ait.ubicity.commons.protocol.Answer;
import at.ac.ait.ubicity.commons.protocol.Command;

/**
 *
 * @author jan
 */
public class AbstractCore implements Runnable {

	protected static AbstractCore singleton;

	// here we keep track of currently running plugins
	protected final Map<UbicityPlugin, PluginContext> plugins = new HashMap<UbicityPlugin, PluginContext>();

	protected final Map<UbicityAddOn, AddOnContext> addOns = new HashMap<UbicityAddOn, AddOnContext>();

	// our elasticsearch indexing client
	protected static final TransportClient esclient;

	// this object is doing actual plugin management for us ( at least the
	// loading & instantiation part )
	protected final PluginManager pluginManager = PluginManagerFactory
			.createPluginManager();

	// the place where the PluginManager is supposed to ( cyclically ) look for
	// new plugins being dumped, in .jar form
	protected final URI pluginURI = new File(Constants.PLUGIN_DIRECTORY_NAME)
			.toURI();

	protected static Logger logger;

	public static String ES_SERVER;
	public static int ES_SERVER_PORT;
	public static String ES_INDEX;
	public static String ES_TYPE;

	static {

		logger = Logger.getLogger(AbstractCore.class.getName());

		Configuration config;

		// get our own Configuration
		try {
			// set necessary stuff for us to ueberhaupt be able to work
			config = new PropertiesConfiguration("core.cfg");
			ES_SERVER = config.getString("core.elasticsearch.host");
			ES_SERVER_PORT = config.getInt("core.elasticsearch.host_port");
			ES_INDEX = config.getString("core.elasticsearch.index");
			ES_TYPE = config.getString("core.elasticsearch.type");
			logger.info("Core : will index to " + ES_SERVER + ":"
					+ ES_SERVER_PORT + "/" + ES_INDEX + " @type " + ES_TYPE);
		} catch (ConfigurationException noConfig) {
			// log this problem and then go along with default configuration
			logger.warning("Core :: could not configure from core.cfg file [ not found, or there was a problem with it ], trying to revert to DefaultConfiguration  : "
					+ noConfig.toString());
		}

		// instantiate an elasticsearch client
		Settings settings = ImmutableSettings.settingsBuilder().build();
		esclient = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(ES_SERVER,
						ES_SERVER_PORT));
		try {

			CreateIndexRequestBuilder createIndexRequestBuilder = esclient
					.admin().indices().prepareCreate(ES_INDEX);
			createIndexRequestBuilder.execute().actionGet();
		} catch (Throwable t) {
			// do nothing, we may get an IndexAlreadyExistsException, but don't
			// care about that, here and now
		}
	}

	/**
	 * 
	 * @return AbstractCore - a Core instance
	 */
	public static AbstractCore getInstance() {
		if (singleton == null)
			singleton = new AbstractCore();
		return singleton;

	}

	/**
	 * 
	 * @param obituary
	 *            The obituary for a consumer, whose thread has just died. We
	 *            use it to get to the rest of our consumer's associated plugin
	 *            information, and properly terminate both the plugin and its
	 *            producer thread. If the plugin does not terminate properly
	 *            within a standard waiting period, we force it stop.
	 */
	public void callBack(Obituary obituary) {
		UbicityPlugin p = obituary.getPlugin();
		plugins.get(p).destroy();
		plugins.remove(p);
		p = null;
		obituary = null;
	}

	/**
	 * 
	 * @param _plugin
	 *            The plugin to register. A Plugin **MUST** call this method in
	 *            order to be recognized by the Core and benefit from its access
	 *            to elasticsearch. It **IS** possible to run as a plugin
	 *            without calling this method; the plugin is then, basically, an
	 *            isolated POJO or - if it wishes to implement the Runnable
	 *            interface - an isolated Thread with the same JVM as the Core.
	 * @return true if registering went OK, false in case of a problem.
	 */
	public final boolean register(UbicityPlugin _plugin) {
		try {
			PluginContext context = new PluginContext(esclient, _plugin);
			_plugin.setContext(context);
			plugins.put(_plugin, context);
			return true;
		} catch (Exception e) {
			logger.warning("caught an exception while trying to register plugin "
					+ _plugin.getName() + " : " + e.toString());
			return false;
		} catch (Error ee) {
			logger.severe("caught an error while trying to register plugin "
					+ _plugin.getName() + ": " + ee.toString());
			return false;
		}

	}

	/**
	 * Offer more than 1 JSONObject simultaneously.
	 * 
	 * @param _json
	 * @param _pC
	 */
	public final void offerBulk(JSONObject[] _json, PluginContext _pC) {
		_pC.getAssociatedProducer().offer(_json);
	}

	@Override
	public void run() {

	}

	public Answer forward(Command _command) {
		return Answer.ACK;
	}

}
