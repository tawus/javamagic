package com.googlecode.tawus.tapestry53;

import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.OptionGroupModel;
import org.apache.tapestry5.OptionModel;
import org.apache.tapestry5.internal.OptionModelImpl;
import org.apache.tapestry5.util.AbstractSelectModel;

import com.googlecode.tawus.tapestry53.entities.Fruit;

public class FruitSelectModel extends AbstractSelectModel
{

   private Iterable<Fruit> fruits;

   public FruitSelectModel(Iterable<Fruit> fruits)
   {
      this.fruits = fruits;
   }
   
   public List<OptionGroupModel> getOptionGroups()
   {
      return null;
   }

   public List<OptionModel> getOptions()
   {
      List<OptionModel> optionModels = new ArrayList<OptionModel>();
      
      for(Fruit fruit: fruits){
         optionModels.add(new OptionModelImpl(fruit.getName(), fruit));
      }
      
      return optionModels;
   }

}
