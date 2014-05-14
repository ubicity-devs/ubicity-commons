package at.ac.ait.ubicity.commons.interfaces;

import at.ac.ait.ubicity.commons.protocol.Answer;
import at.ac.ait.ubicity.commons.protocol.Command;

/**
 *
 * @author jan van oort
 */
public interface ReverseControllableMediumPlugin extends UbicityPlugin {

	public Answer execute(Command _command);
}
