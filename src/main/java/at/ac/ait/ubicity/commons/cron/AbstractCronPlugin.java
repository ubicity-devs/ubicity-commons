package at.ac.ait.ubicity.commons.cron;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import at.ac.ait.ubicity.commons.interfaces.CronPlugin;
import at.ac.ait.ubicity.commons.interfaces.CronTask;

public abstract class AbstractCronPlugin implements CronPlugin {

	private static final Logger logger = Logger
			.getLogger(AbstractCronPlugin.class);

	private static SchedulerFactory sf = new StdSchedulerFactory();
	private Scheduler sched;

	@Override
	public void initCron(List<CronTask> tasks) throws UbicityCronException {
		try {
			sched = sf.getScheduler();
			if (!sched.isStarted()) {
				sched.start();
			}

			for (CronTask task : tasks) {

				String key = this.getName() + "-" + task.getName();

				if (!sched.checkExists(JobKey.jobKey(key))) {
					JobDetail job = JobBuilder.newJob(task.getClass())
							.withIdentity(JobKey.jobKey(key)).build();

					job.getJobDataMap().putAll(task.getProperties());

					TriggerBuilder<Trigger> tb = TriggerBuilder.newTrigger();
					tb.withSchedule(CronScheduleBuilder
							.cronSchedule(new CronExpression(task
									.getTimeInterval())));
					tb.withIdentity(key);

					sched.scheduleJob(job, tb.build());
					logger.info("Registered task '" + key + "' with schedule '"
							+ task.getTimeInterval() + "'");
				}
			}

		} catch (SchedulerException | ParseException e) {
			throw new UbicityCronException(e);
		}
	}

	@Override
	public void shutdown() {
		if (sched != null) {
			try {
				sched.shutdown(true);
			} catch (SchedulerException e) {
				logger.warn("Exc. caught while shutting down scheduler "
						+ this.getName());
			}

		}
	}
}
