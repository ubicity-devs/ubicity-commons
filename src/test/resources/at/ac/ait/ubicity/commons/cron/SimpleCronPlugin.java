package at.ac.ait.ubicity.commons.cron;

import java.util.Arrays;
import java.util.List;

import at.ac.ait.ubicity.commons.interfaces.CronTask;

public class SimpleCronPlugin extends AbstractCronPlugin {

	protected SimpleCronPlugin() throws UbicityCronException {
		super();
	}

	@Override
	public String getName() {
		return "ChronShed";
	}

	@Override
	public void init() {
	}

	@Override
	public void shutdown() {
	}

	@Override
	public List<CronTask> getJobs() {
		return Arrays.asList(new SimpleTask());
	}
}
