package com.googlecode.tawus.tapestry53;

import org.apache.tapestry5.ValueEncoder;

import com.googlecode.tawus.tapestry53.entities.Fruit;

public class FruitValueEncoder implements ValueEncoder<Fruit>
{
   private Iterable<Fruit> fruits;

   public FruitValueEncoder(Iterable<Fruit> fruits)
   {
      this.fruits = fruits;
   }

   public String toClient(Fruit fruit)
   {
      return fruit.getName();
   }

   public Fruit toValue(String fruitName)
   {
      for(Fruit fruit: fruits)
      {
         if(fruit.getName().equals(fruitName))
         {
            return fruit;
         }
      }
      
      throw new RuntimeException("Invalid fruit name returned from client: " + fruitName);
   }

}
