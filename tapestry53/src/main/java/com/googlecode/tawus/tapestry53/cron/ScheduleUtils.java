package com.googlecode.tawus.tapestry53.cron;

import org.apache.tapestry5.ioc.services.cron.IntervalSchedule;
import org.apache.tapestry5.ioc.services.cron.Schedule;

public class ScheduleUtils
{
    public static Schedule hourlySchedule(int hours)
    {
        return new IntervalSchedule(hours * 60 * 60 * 1000L);
    }

    public static Schedule minutelySchedule(int minutes)
    {
        return new IntervalSchedule(minutes * 60 * 1000L);
    }

    public static Schedule secondlySchedule(int seconds)
    {
        return new IntervalSchedule(seconds * 1000L);
    }
}

