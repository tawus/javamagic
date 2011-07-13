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

package com.googlecode.tawus.perf4j.pages;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.perf4j.aop.Profiled;
import org.slf4j.Logger;

import com.googlecode.tawus.perf4j.services.TestService;

public class TestPage
{
   @Inject
   private Logger logger;
   
   @Inject
   private TestService testService;
   
   void onActivate()
   {
      testService.test();   
   }
   
   @Profiled(level = "WARN")
   void onClick() throws InterruptedException
   {
      Thread.sleep(1500);
      logger.debug("clicked");
   }

}
