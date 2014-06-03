package at.ac.ait.ubicity.commons.broker;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;
import at.ac.ait.ubicity.commons.broker.exceptions.UbicityBrokerException;
import at.ac.ait.ubicity.commons.interfaces.BaseUbicityPlugin;

public interface UbicityBroker extends BaseUbicityPlugin {

	void register(BrokerConsumer consumer);

	void deRegister(BrokerConsumer consumer);

	void publish(EventEntry event) throws UbicityBrokerException;
}
