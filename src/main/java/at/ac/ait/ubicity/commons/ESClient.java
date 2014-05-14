package at.ac.ait.ubicity.commons;

import java.util.logging.Logger;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESClient {

	private final Client client;

	private static final Logger logger = Logger.getLogger(ESClient.class
			.getName());

	public ESClient(String server, int port, String cluster) {

		// instantiate an elasticsearch client
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", cluster).build();

		client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress(server,
						port));

		logger.info("Connected to " + server + ":" + port + " - Cluster: "
				+ cluster);
	}

	/**
	 * Creates the given Index if it does not exist.
	 * 
	 * @param idx
	 */
	public void createIndex(String idx) {
		try {
			CreateIndexRequestBuilder createIndexRequestBuilder = client
					.admin().indices().prepareCreate(idx);
			createIndexRequestBuilder.execute().actionGet();
		} catch (Throwable t) {
			// do nothing, we may get an IndexAlreadyExistsException, but
			// don't
			// care about that, here and now
		}
	}

	/**
	 * Returns a IndexRequestBuidler for the given ES index and type.
	 * 
	 * @param idx
	 * @param type
	 * @return
	 */
	public IndexRequestBuilder getSingleRequestBuilder(String idx, String type) {
		return client.prepareIndex(idx, type);
	}

	public BulkRequestBuilder getBulkRequestBuilder() {
		return client.prepareBulk();
	}

	public void close() {
		client.close();
	}

}
