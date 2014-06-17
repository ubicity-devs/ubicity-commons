package at.ac.ait.ubicity.commons.broker;

import javax.jms.JMSException;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.fusesource.stomp.jms.StompJmsConnection;
import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;
import at.ac.ait.ubicity.commons.util.PropertyLoader;

public abstract class AbstractBrokerClient {

	private StompJmsConnection connection;
	private Session session;
	private String destinationPrefix;

	private static final Logger logger = Logger
			.getLogger(AbstractBrokerClient.class);

	/**
	 * Sets up the Broker connection with configured username & password.
	 * 
	 * @param user
	 * @param pwd
	 * @throws UbicityBrokerException
	 */
	protected void init(String user, String pwd) throws UbicityBrokerException {
		PropertyLoader config = new PropertyLoader(
				AbstractBrokerClient.class.getResource("/broker_client.cfg"));

		destinationPrefix = config.getString("addon.apollo.dest.prefix");

		StompJmsConnectionFactory factory = new StompJmsConnectionFactory();
		factory.setBrokerURI(config.getString("addon.apollo.client.host"));

		try {
			connection = (StompJmsConnection) factory.createConnection(user,
					pwd);
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
			throw new UbicityBrokerException(e);
		}
	}

	public void shutdown() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (JMSException e) {
			logger.warn("Close connection threw exception ", e);
		}
	}

	protected Session getSession() {
		return this.session;
	}

	protected StompJmsConnection getConnection() {
		return this.connection;
	}

	protected String calcDestination(String destination) {
		String dest = destination.toLowerCase();
		dest = dest.replace("/topic/", "/topic/" + destinationPrefix);
		dest = dest.replace("/queue/", "/queue/" + destinationPrefix);
		return dest;
	}
}
