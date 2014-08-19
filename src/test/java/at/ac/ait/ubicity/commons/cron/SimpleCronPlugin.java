package at.ac.ait.ubicity.commons.cron;

import java.util.Arrays;

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
		try {
			initCron(Arrays.asList(new SimpleTask()));
		} catch (UbicityCronException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown() {
	}
}
