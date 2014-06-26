package at.ac.ait.ubicity.commons.cron;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import at.ac.ait.ubicity.commons.interfaces.CronTask;

public abstract class AbstractJob implements CronTask {

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		executeTask();
	}

	/**
	 * Implementation of the triggered Task.
	 */
	@Override
	public abstract void executeTask();
}