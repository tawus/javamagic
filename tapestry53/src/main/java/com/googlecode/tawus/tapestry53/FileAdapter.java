package com.googlecode.tawus.tapestry53;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.tapestry5.tree.TreeModelAdapter;

public class FileAdapter implements TreeModelAdapter<File>
{

   public boolean isLeaf(File file)
   {
      return !file.isDirectory();
   }

   public boolean hasChildren(File file)
   {
      return file.isDirectory();
   }

   public List<File> getChildren(File file)
   {
      return Arrays.asList(file.listFiles());
   }

   public String getLabel(File file)
   {
      return file.getName();
   }

}
