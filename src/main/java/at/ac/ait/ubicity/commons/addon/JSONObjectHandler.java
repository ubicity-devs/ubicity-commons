/**
Copyright (C) 2013 AIT / Austrian Institute of Technology
http://www.ait.ac.at

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program. If not, see http://www.gnu.org/licenses/agpl-3.0.html
 */
package at.ac.ait.ubicity.commons.addon;

import java.util.logging.Logger;

import org.elasticsearch.client.transport.TransportClient;

import com.lmax.disruptor.EventHandler;

/**
 *
 * @author jan
 */
public class JSONObjectHandler implements EventHandler<JSONObjectWrapper> {

	public static TransportClient esclient;

	private final static Logger logger = Logger
			.getLogger(JSONObjectHandler.class.getName());

	@Override
	public void onEvent(JSONObjectWrapper event, long sequence,
			boolean endOfBatch) throws Exception {

		// TODO: Christoph: Find a Way to give the ESClient efficient to the
		// class
		/*
		 * try { IndexRequestBuilder indexRequestBuilder =
		 * esclient.prepareIndex( AbstractCore.index, AbstractCore.type); String
		 * __id = new StringBuilder() .append(System.currentTimeMillis())
		 * .append(System.nanoTime()).toString(); try {
		 * indexRequestBuilder.setSource(event.toString());
		 * indexRequestBuilder.setId(__id);
		 * 
		 * IndexResponse response = indexRequestBuilder.execute() .actionGet();
		 * 
		 * if (response == null) { logger.warning(this.getClass().getName() +
		 * " : got a <null> IndexResponse when trying to index against elasticsearch "
		 * ); } } catch (Throwable tt) { tt.printStackTrace(); }
		 * 
		 * } catch (JSONException somethingWrong) {
		 * logger.fine("caught a JSONException : " + somethingWrong.toString());
		 * }
		 */
	}

}