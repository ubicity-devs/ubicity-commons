package at.ac.ait.ubicity.commons.interfaces;

import java.util.List;

/**
 * Interface for time triggered plugins
 * 
 * @author ruggenthalerc
 *
 */
public interface CronPlugin extends UbicityPlugin {

	/**
	 * List of all Jobs to be executed.
	 * 
	 * @return
	 */
	List<CronTask> getJobs();
}
