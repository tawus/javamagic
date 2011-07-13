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

import org.apache.tapestry5.model.MutableComponentModel;
import org.apache.tapestry5.plastic.PlasticClass;
import org.apache.tapestry5.plastic.PlasticMethod;
import org.apache.tapestry5.services.transform.ComponentClassTransformWorker2;
import org.apache.tapestry5.services.transform.TransformationSupport;
import org.perf4j.aop.Profiled;

public class Perf4JWorker implements ComponentClassTransformWorker2
{
   private Perf4JServiceAdvisor advisor;

   public Perf4JWorker(Perf4JServiceAdvisor advisor)
   {
      this.advisor = advisor;
   }

   public void transform(final PlasticClass plasticClass, TransformationSupport support, MutableComponentModel model)
   {
      for(final PlasticMethod method : plasticClass.getMethodsWithAnnotation(Profiled.class))
      {
         method.addAdvice(advisor.getAdvice(method.getAnnotation(Profiled.class), plasticClass.getClassName()));
      }
   }

}
