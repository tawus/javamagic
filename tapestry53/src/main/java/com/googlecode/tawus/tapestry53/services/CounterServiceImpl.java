package com.googlecode.tawus.tapestry53.services;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterServiceImpl implements CounterService
{
   private AtomicInteger counter = new AtomicInteger();

   public void reset()
   {
      counter.set(0);
   }

   public int getValue()
   {
      return counter.get();
   }

   public void increment()
   {
      counter.addAndGet(1);
   }

}
