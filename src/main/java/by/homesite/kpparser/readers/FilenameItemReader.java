package by.homesite.kpparser.readers;

import by.homesite.kpparser.model.FileInfo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import static by.homesite.kpparser.utils.FilenameUtils.extractTitleFromFilename;
import static by.homesite.kpparser.utils.FilenameUtils.extractYearFromFilename;

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
      FileInfo filenameEntity = new FileInfo();

      filenameEntity.setName(myresource.getFile().getName());
      filenameEntity.setTitle(extractTitleFromFilename(myresource.getFile().getName()));
      filenameEntity.setYear(extractYearFromFilename(myresource.getFile().getName()));

      return filenameEntity;
   }



}
