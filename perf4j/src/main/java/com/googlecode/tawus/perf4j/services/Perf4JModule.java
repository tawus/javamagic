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

package com.googlecode.tawus.perf4j.services;

import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;

import com.googlecode.tawus.perf4j.internal.Log4JStopWatchSource;
import com.googlecode.tawus.perf4j.internal.Perf4JServiceAdvisor;
import com.googlecode.tawus.perf4j.internal.Perf4JServiceAdvisorImpl;
import com.googlecode.tawus.perf4j.internal.Perf4JWorker;

public class Perf4JModule {
   public static void bind(ServiceBinder binder){
      binder.bind(StopWatchSource.class, Log4JStopWatchSource.class);
      binder.bind(Perf4JServiceAdvisor.class, Perf4JServiceAdvisorImpl.class);
   }

   @Contribute(ComponentClassTransformWorker2.class)
   public void contributeComponentClassTransformWorker2(
         OrderedConfiguration<ComponentClassTransformWorker2> configuration, 
         Perf4JServiceAdvisor advisor){
      configuration.add("perf4J", new Perf4JWorker(advisor)); 
   }

}
