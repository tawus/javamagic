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

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.perf4j.aop.Profiled;

/**
 * A method advisor for profiling a method using Perf4j
 */
public interface Perf4JServiceAdvisor
{
   /**
    * Advise a method
    */
   void advise(MethodAdviceReceiver receiver);

   /**
    * Get advice for profiling
    */
   MethodAdvice getAdvice(Profiled annotation, String className);
}
