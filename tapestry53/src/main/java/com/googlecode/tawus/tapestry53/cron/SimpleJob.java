package com.googlecode.tawus.tapestry53.cron;

import com.googlecode.tawus.tapestry53.services.CounterService;

public class SimpleJob implements Runnable
{
   
    private CounterService counterService;

    public SimpleJob(CounterService counterService)
   {
      this.counterService = counterService;
   }

   public void run()
    {
        counterService.increment();
    }
}

