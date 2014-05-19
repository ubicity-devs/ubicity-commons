package at.ac.ait.ubicity.commons.broker;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;

import com.lmax.disruptor.EventHandler;

public interface BrokerConsumer extends EventHandler<EventEntry> {

	String getName();
}
