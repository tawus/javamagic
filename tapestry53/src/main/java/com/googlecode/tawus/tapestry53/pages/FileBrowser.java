package com.googlecode.tawus.tapestry53.pages;

import java.io.File;
import java.util.Arrays;

import org.apache.tapestry5.ValueEncoder;
import org.apache.tapestry5.tree.DefaultTreeModel;
import org.apache.tapestry5.tree.TreeModel;

import com.googlecode.tawus.tapestry53.FileAdapter;

public class FileBrowser
{
   private File directory;
   
   void onActivate()
   {
      directory = new File(".");
   }

   public TreeModel<File> getFileModel()
   {
      ValueEncoder<File> encoder = new ValueEncoder<File>()
      {

         public String toClient(File file)
         {
            return file.getAbsolutePath();
         }

         public File toValue(String name)
         {
            return new File(name);
         }
      };

      return new DefaultTreeModel<File>(encoder, new FileAdapter(), Arrays.asList(getRootDirectory().listFiles()));

   }

   public File getRootDirectory()
   {
      return directory;
   }

}
