package at.ac.ait.ubicity.commons.broker;

/**
 * Interface for the LMAX Producers.
 * 
 * @author ruggenthalerc
 *
 */
public interface BrokerProducer {

	/**
	 * Returns the unique producer name.
	 * 
	 * @return
	 */
	String getName();
}
