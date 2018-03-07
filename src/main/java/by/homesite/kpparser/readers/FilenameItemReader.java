package by.homesite.kpparser.readers;

import by.homesite.kpparser.model.FileInfo;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.core.io.Resource;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static by.homesite.kpparser.utils.FilenameUtils.extractTitleFromFilename;
import static by.homesite.kpparser.utils.FilenameUtils.extractYearFromFilename;

/**
 * @author alex on 5/3/17.
 */
public class FilenameItemReader extends FlatFileItemReader<FileInfo> {

   private Resource myresource;
   private final Queue<String> alreadyRead = new ConcurrentLinkedQueue<>();

   @Override
   public void setResource(Resource var1) {
      super.setResource(var1);
      myresource = var1;
   }

   @Override
   protected FileInfo doRead() throws Exception {

      if (alreadyRead.contains(myresource.getFile().getName())) {
         return null;
      }

      FileInfo filenameEntity = new FileInfo();

      filenameEntity.setName(myresource.getFile().getName());
      filenameEntity.setTitle(extractTitleFromFilename(myresource.getFile().getName()));
      filenameEntity.setYear(extractYearFromFilename(myresource.getFile().getName()));

      alreadyRead.add(myresource.getFile().getName());

      return filenameEntity;
   }



}
