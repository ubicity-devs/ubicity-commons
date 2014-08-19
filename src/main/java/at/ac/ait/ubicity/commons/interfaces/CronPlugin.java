package at.ac.ait.ubicity.commons.interfaces;

import java.util.List;

import at.ac.ait.ubicity.commons.cron.UbicityCronException;

/**
 * Interface for time triggered plugins
 * 
 * @author ruggenthalerc
 *
 */
public interface CronPlugin extends UbicityPlugin {

	void initCron(List<CronTask> tasks) throws UbicityCronException;
}
