import db.PropertiesCreator;
import grab.Grabber;
import model.Company;
import model.store.PsqlStore;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import parse.FinanceParse;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException {


       FinanceParse parse = new FinanceParse();
        Grabber grabber = new Grabber(parse);
        Scheduler scheduler = null;
        try {
            scheduler = grabber.scheduler();
            grabber.init(parse, scheduler);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
