package com.googlecode.tawus.tapestry53.pages;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.PersistenceConstants;
import org.apache.tapestry5.SelectModel;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;

import com.googlecode.tawus.tapestry53.FruitSelectModel;
import com.googlecode.tawus.tapestry53.FruitValueEncoder;
import com.googlecode.tawus.tapestry53.entities.Fruit;

public class ChecklistDemo
{
   @SuppressWarnings("unused")
   @Property
   @Persist(PersistenceConstants.FLASH)
   private List<Fruit> selectedFruits;
   
   private List<Fruit> fruits;

   @SuppressWarnings("unused")
   @Property(write = false)
   private SelectModel fruitModel;

   @SuppressWarnings("unused")
   @Property(write = false)
   private FruitValueEncoder fruitEncoder;

   void onActivate()
   {
      addFruits();
      fruitModel = new FruitSelectModel(fruits);
      fruitEncoder = new FruitValueEncoder(fruits);
   }

   private void addFruits()
   {
      fruits = new ArrayList<Fruit>();
      
      for(String fruitName : new String[] { "Apple", "Banana", "Mango", "Melon" })
      {
         fruits.add(new Fruit(fruitName));
      }
   }
   
}
