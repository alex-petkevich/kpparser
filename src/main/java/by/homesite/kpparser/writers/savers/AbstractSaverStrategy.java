package by.homesite.kpparser.writers.savers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author alex on 9/17/17.
 */
public abstract class AbstractSaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(AbstractSaverStrategy.class);

   @Value("${saveDescriptionsFolder}")
   protected String saveDescriptionsFolder;

   void saveImage(String img, String fileName) {
      if (img.lastIndexOf('.') < 0)
         return;

      String ext = img.substring(img.lastIndexOf('.'));

      Path outputFileName = Paths.get(saveDescriptionsFolder + fileName + ext);

      try(InputStream in = new URL(img).openStream()){
         Files.copy(in, outputFileName);
      } catch (MalformedURLException e) {
         log.error("Incorrect URL for image {}", img);
      } catch (IOException e) {
         log.error("Can't save image {}", fileName);
      }

   }

}
