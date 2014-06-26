package at.ac.ait.ubicity.commons.interfaces;

import at.ac.ait.ubicity.commons.protocol.Answer;
import at.ac.ait.ubicity.commons.protocol.Command;
import at.ac.ait.ubicity.commons.protocol.Medium;

/**
 *
 * @author jan van oort
 */
public interface JiTPlugin extends UbicityPlugin {

	/**
	 * @param command
	 * @return
	 */
	public Answer execute(Command command);

	/**
	 * @param med
	 * @return
	 */
	public boolean isResponsible(Medium med);
}
