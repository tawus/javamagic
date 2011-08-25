package com.googlecode.tawus.tapestry53.pages;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.cron.PeriodicExecutor;
import org.apache.tapestry5.ioc.services.cron.PeriodicJob;

import com.googlecode.tawus.tapestry53.cron.ScheduleUtils;
import com.googlecode.tawus.tapestry53.cron.SimpleJob;
import com.googlecode.tawus.tapestry53.services.CounterService;

/**
 * A page to demonstrate the usage of a Simple Scheduler
 */
public class SimpleJobDemo
{
   @Inject
   private PeriodicExecutor executor;

   @Persist
   @Property
   private PeriodicJob periodicJob;

   @Inject
   @Property
   private CounterService counterService;

   @InjectComponent
   private Zone zone;

   void onActivate()
   {
      if(periodicJob == null)
      {
         start();
      }
   }

   Object onZoneRefresh()
   {
      return periodicJob.isCanceled() ? null : zone.getBody();
   }

   void onCancel()
   {
      periodicJob.cancel();
   }

   void onRestart()
   {
      start();
   }

   private void start()
   {
      counterService.reset();
      SimpleJob job = new SimpleJob(counterService);
      periodicJob = executor.addJob(ScheduleUtils.secondlySchedule(1), "My StopWatch Schedule", job);
   }

}
