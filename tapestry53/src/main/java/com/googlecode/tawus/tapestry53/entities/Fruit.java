package com.googlecode.tawus.tapestry53.entities;

public class Fruit
{
   private String name;

   public Fruit(String name)
   {
      this.setName(name);
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public String getName()
   {
      return name;
   }
   
   @Override
   public int hashCode(){
      return name.hashCode();
   }
   
   @Override
   public boolean equals(Object value){
      if(value == this){
         return true;
      }
      
      if(value == null || !(value instanceof Fruit))
      {
         return false;
      }
      
      Fruit other = (Fruit)value;
      
      return getName().equals(other.getName());
   }
   
   @Override
   public String toString(){
      return name;
   }

}
