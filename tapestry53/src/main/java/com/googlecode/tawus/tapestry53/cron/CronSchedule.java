package com.googlecode.tawus.tapestry53.cron;

import java.text.ParseException;
import java.util.Date;

import org.apache.tapestry5.ioc.services.cron.Schedule;
import org.quartz.CronExpression;

public class CronSchedule implements Schedule {
    private CronExpression cron;

    public CronSchedule(String cronExpression) {
        try {
            cron = new CronExpression(cronExpression);
        } catch (ParseException e) {
            throw new RuntimeException("Could not parse cron expression", e);
        }
    }

    public long firstExecution() {
        return cron.getNextValidTimeAfter(new Date()).getTime();
    }

    public long nextExecution(long previousExecution) {
        return cron.getNextValidTimeAfter(new Date(previousExecution)).getTime();
    }

}
