package plasticdemo.controlled;

import plasticdemo.annotations.Property;
import plasticdemo.annotations.Run;

public class Foo {

   @SuppressWarnings("unused")
   @Property
   private String name;
   
   @SuppressWarnings("unused")
   @Property
   private int _num;
   
   private boolean didRun = false;
   
   @Run
   public void runThisMethod(){
      setDidRun(true);
   }

   public void setDidRun(boolean didRun) {
      this.didRun = didRun;
   }

   public boolean isDidRun() {
      return didRun;
   }
   
   
}
