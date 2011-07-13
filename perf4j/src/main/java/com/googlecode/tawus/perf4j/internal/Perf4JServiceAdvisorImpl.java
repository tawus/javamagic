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

import java.lang.reflect.Method;

import org.apache.tapestry5.ioc.MethodAdviceReceiver;
import org.apache.tapestry5.plastic.MethodAdvice;
import org.apache.tapestry5.plastic.MethodInvocation;
import org.perf4j.StopWatch;
import org.perf4j.aop.Profiled;

import com.googlecode.tawus.perf4j.services.StopWatchSource;

public class Perf4JServiceAdvisorImpl implements Perf4JServiceAdvisor {

   private StopWatchSource stopWatchSource;

   public Perf4JServiceAdvisorImpl(StopWatchSource stopWatchSource) {
      this.stopWatchSource = stopWatchSource;
   }

   public void advise(final MethodAdviceReceiver receiver) {      
      for (final Method method : receiver.getInterface().getMethods())
      {
         final Profiled annotation = method.getAnnotation(Profiled.class);
         if (annotation != null) {
            receiver.adviseMethod(method, getAdvice(annotation, receiver.getInterface().getName()));
         }
      }
   }

   public MethodAdvice getAdvice(final Profiled annotation, final String className){
      return  new MethodAdvice() {
         public void advise(MethodInvocation invocation) {
            invoke(invocation, annotation, className);
         }
      };
   }
   
   private void invoke(MethodInvocation invocation, Profiled annotation,
         String className) {
      final StopWatch watch = stopWatchSource.create(annotation);
      invocation.proceed();
      String tag;
      if (Profiled.DEFAULT_TAG_NAME.equals(annotation.tag())) {
         tag = className + "." + invocation.getMethod().getName();
      } else {
         tag = annotation.tag();
      }
      watch.stop(tag, annotation.message());
   }

}
