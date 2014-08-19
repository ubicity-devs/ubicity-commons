package at.ac.ait.ubicity.commons.cron;

import java.util.Date;

public class SimpleTask extends AbstractTask {

	@Override
	public String getName() {
		return "every-min";
	}

	@Override
	public String getTimeInterval() {
		return "0 * * * * ?";
	}

	@Override
	public void executeTask() {
		System.out.println("Run at " + new Date());
	}

}
