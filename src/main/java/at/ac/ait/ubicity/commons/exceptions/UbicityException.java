package at.ac.ait.ubicity.commons.exceptions;

public class UbicityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UbicityException(Throwable t) {
		super(t);
	}

	public UbicityException(String msg) {
		super(msg);
	}
}
