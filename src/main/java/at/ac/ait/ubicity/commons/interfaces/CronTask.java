package at.ac.ait.ubicity.commons.interfaces;

import java.util.Map;

import org.quartz.Job;

/**
 * Interface for time triggered plugins
 */
public interface CronTask extends Job {

	String getName();

	void setName(String name);

	/**
	 * String representation of cron trigger.
	 * 
	 * @See http://quartz-scheduler.org/documentation/quartz-1.x/tutorials/ crontrigger
	 * @return
	 */
	String getTimeInterval();

	/**
	 * Set String representation of cron trigger.
	 * 
	 * @See http://quartz-scheduler.org/documentation/quartz-1.x/tutorials/ crontrigger
	 * @return
	 */
	void setTimeInterval(String interval);

	/**
	 * Set properties for task execution
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	void setProperty(String key, Object value);

	/**
	 * Return Property by key or null;
	 * 
	 * @param key
	 * @return
	 */
	Object getProperty(String key);

	/**
	 * Return all properties
	 * 
	 * @param key
	 * @return
	 */
	Map<String, Object> getProperties();

	/**
	 * Actual implementation of the task.
	 */
	void executeTask();
}
