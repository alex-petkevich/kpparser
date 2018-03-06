package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.config.Constants;
import by.homesite.kpparser.config.SaverTypes;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author alex on 9/16/17.
 */
@Component(Constants.JSON_OUTPUT_FORMAT)
public class JsonSaverStrategy extends AbstractSaverStrategy implements SaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(JsonSaverStrategy.class);

   @Value("${rescanExistingDescriptions}")
   private Boolean rescanExistingDescriptions;

   private final Gson gson;

   @Autowired
   public JsonSaverStrategy(Gson gson) {
      this.gson = gson;
   }

   @Override
   public void doSave(Film item) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + item.getFileName() + Constants.JSON_OUTPUT_EXTENSION);
      if (isFileExists(item.getFileName())) {
         return;
      }

      try (Writer out = Files.newBufferedWriter(outputFileName, Charset.defaultCharset())) {

         gson.toJson(item, out);

      } catch (IOException e) {
         log.error("Can't write output file {}", SaverTypes.JSON.toString());
      }

      if (!StringUtils.isEmpty(item.getImg())) {
         saveImage(item.getImg(), item.getFileName());
      }
   }

   @Override
   public boolean isFileExists(String fileName) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + fileName + Constants.JSON_OUTPUT_EXTENSION);
      return  (Files.exists(outputFileName) && Boolean.TRUE != rescanExistingDescriptions);
   }

}
