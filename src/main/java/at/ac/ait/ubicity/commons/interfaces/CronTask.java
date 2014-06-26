package at.ac.ait.ubicity.commons.interfaces;

import org.quartz.Job;

/**
 * Interface for time triggered plugins
 * 
 * @author ruggenthalerc
 *
 */
public interface CronTask extends Job {

	String getName();

	/**
	 * String representation of cron trigger.
	 * 
	 * @See http://quartz-scheduler.org/documentation/quartz-1.x/tutorials/
	 *      crontrigger
	 * @return
	 */
	String getTimeInterval();

	/**
	 * Actual implementation of the task.
	 */
	void executeTask();
}
