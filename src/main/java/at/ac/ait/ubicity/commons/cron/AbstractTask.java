package at.ac.ait.ubicity.commons.cron;

import java.util.HashMap;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;

import at.ac.ait.ubicity.commons.interfaces.CronTask;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class AbstractTask implements CronTask {
	private String timeInterval;
	private final HashMap<String, Object> map = new HashMap<String, Object>();

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {

		map.putAll(ctx.getJobDetail().getJobDataMap().getWrappedMap());
		executeTask();

		// Write back the map to context
		ctx.getJobDetail().getJobDataMap().putAll(map);
	}

	/**
	 * Implementation of the triggered Task.
	 */
	@Override
	public abstract void executeTask();

	@Override
	public String getName() {
		return (String) map.get("name");
	}

	@Override
	public void setName(String name) {
		map.put("name", name);
	}

	@Override
	public String getTimeInterval() {
		return this.timeInterval;
	}

	@Override
	public void setTimeInterval(String interval) {
		this.timeInterval = interval;
	}

	@Override
	public void setProperty(String key, Object value) {
		map.put(key, value);
	}

	@Override
	public Object getProperty(String key) {
		return map.get(key);
	}

	@Override
	public HashMap<String, Object> getProperties() {
		return map;
	}
}