package at.ac.ait.ubicity.commons;

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

import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;

import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.action.index.IndexResponse;
import org.json.JSONException;
import org.json.JSONObject;

import at.ac.ait.ubicity.commons.interfaces.UbicityPlugin;
import at.ac.ait.ubicity.commons.interfaces.UbicityPlugin.PluginConfig;

/**
 *
 * @author Jan van Oort
 * @version 0.1
 */
public class JSONConsumer implements Runnable {

	private final UbicityPlugin plugin;

	private final BlockingQueue<JSONObject> queue;

	private final ESClient client;

	private static final Logger logger = Logger.getLogger(JSONConsumer.class
			.getName());

	public JSONConsumer(UbicityPlugin _plugin, ESClient _client,
			BlockingQueue<JSONObject> _queue) {
		plugin = _plugin;
		queue = _queue;
		client = _client;
	}

	@Override
	public final void run() {

		while (true) {
			try {
				JSONObject json = queue.take();
				try {

					IndexRequestBuilder indexRequestBuilder = client
							.getSingleRequestBuilder(plugin
									.getConfigEntry(PluginConfig.ES_INDEX),
									plugin.getConfigEntry(PluginConfig.ES_TYPE));

					String __id = new StringBuilder()
							.append(System.currentTimeMillis())
							.append(System.nanoTime()).toString();
					try {
						indexRequestBuilder.setSource(json.toString());
						indexRequestBuilder.setId(__id);

						IndexResponse response = indexRequestBuilder.execute()
								.actionGet();

						if (response == null) {
							logger.warning(this.getClass().getName()
									+ " : got a <null> IndexResponse when trying to index against elasticsearch ");
						}
					} catch (Throwable tt) {
						tt.printStackTrace();
					}

				} catch (JSONException somethingWrong) {
					logger.warning("caught a JSONException : "
							+ somethingWrong.toString());
				}
				if (json instanceof ConsumerPoison)
					AbstractCore.getInstance().callBack(new Obituary(plugin));

			} catch (InterruptedException _interrupt) {
				Thread.interrupted();
				continue;
			}
		}
	}
}
