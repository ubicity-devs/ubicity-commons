package at.ac.ait.ubicity.commons.exceptions;

public class UbicityBrokerException extends Exception {

	private static final long serialVersionUID = 1L;

	public enum BrokerMsg {
		PRODUCER_NOT_EXISTENT, UNKOWN
	}

	private BrokerMsg msg = BrokerMsg.UNKOWN;

	public UbicityBrokerException(BrokerMsg msg) {
		this.msg = msg;
	}

	public UbicityBrokerException(BrokerMsg msg, Throwable t) {
		super(t);
		this.msg = msg;
	}

	public UbicityBrokerException(Throwable t) {
		super(t);
	}

	public UbicityBrokerException() {
	}

	public BrokerMsg getBrokerMessage() {
		return this.msg;
	}

}
