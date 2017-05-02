package by.homesite.kpparser.readers;

import by.homesite.kpparser.model.FileInfo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

/**
 * @author alex on 5/3/17.
 */
public class FilenameItemReader extends FlatFileItemReader<FileInfo> {

   private Resource myresource;

   @Override
   public void setResource(Resource var1) {
      super.setResource(var1);
      myresource = var1;
   }

   @Override
   protected FileInfo doRead() throws Exception {
      FileInfo filenameEntity = super.doRead();
      if (filenameEntity == null) {
         return null;
      }
      filenameEntity.setName(myresource.getFile().getName());
      return filenameEntity;
   }

}
