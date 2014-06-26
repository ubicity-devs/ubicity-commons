package at.ac.ait.ubicity.commons.cron;

import org.junit.Ignore;
import org.junit.Test;

public class CronTest {

	@Test
	@Ignore
	public void runTask() throws Exception {

		new SimpleCronPlugin();

		while (true)
			Thread.sleep(1000);

	}

}
