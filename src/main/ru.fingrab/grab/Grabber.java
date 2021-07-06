
package grab;


import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import parse.FinanceParse;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Grabber {
    FinanceParse financeParse;

    public Grabber(FinanceParse financeParse) {
        this.financeParse = financeParse;
    }


    public Scheduler scheduler() throws SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        return scheduler;
    }

    public void init(FinanceParse financeParse, Scheduler scheduler) throws SchedulerException {
        JobDataMap data = new JobDataMap();
        data.put("parse", financeParse);
        JobDetail job = newJob(GrabJob.class)
                .usingJobData(data)
                .build();
        SimpleScheduleBuilder times = simpleSchedule()
                .withIntervalInSeconds(600)
                .repeatForever();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(times)
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    public static class GrabJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Start");
            JobDataMap map = context.getJobDetail().getJobDataMap();
            FinanceParse financeParse = (FinanceParse) map.get("parse");
            financeParse.parseAll();
            financeParse.addPricesInDb();
            System.out.println("finish");
        }
    }
}

