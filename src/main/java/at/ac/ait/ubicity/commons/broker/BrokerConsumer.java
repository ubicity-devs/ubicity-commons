package at.ac.ait.ubicity.commons.broker;

import at.ac.ait.ubicity.commons.broker.events.EventEntry;

import com.lmax.disruptor.EventHandler;

public abstract class BrokerConsumer implements EventHandler<EventEntry> {

	public abstract String getName();

	@Override
	public void onEvent(EventEntry event, long sequence, boolean endOfBatch)
			throws Exception {
		onReceived(event);
	}

	public abstract void onReceived(EventEntry msg);
}
