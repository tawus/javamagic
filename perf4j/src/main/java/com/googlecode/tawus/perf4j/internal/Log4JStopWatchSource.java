// 
// Copyright 2011 Taha Hafeez Siddiqi
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//   http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// 

package com.googlecode.tawus.perf4j.internal;

import org.apache.log4j.Level;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;
import org.perf4j.log4j.Log4JStopWatch;

import com.googlecode.tawus.perf4j.services.StopWatchSource;

public class Log4JStopWatchSource implements StopWatchSource {
   
   public Log4JStopWatchSource(){
   }

   public StopWatch create(Profiled profiled) {
      Level logLevel = Level.toLevel(profiled.level()); 
      Log4JStopWatch stopWatch = new Log4JStopWatch();
      stopWatch.setNormalAndSlowSuffixesEnabled(profiled.normalAndSlowSuffixesEnabled());
      stopWatch.setTag(profiled.tag());
      stopWatch.setTimeThreshold(profiled.timeThreshold());
      stopWatch.setNormalPriority(logLevel);
      stopWatch.setExceptionPriority(logLevel);
      return stopWatch;
   }

}
