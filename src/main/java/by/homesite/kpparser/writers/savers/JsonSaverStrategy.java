package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.utils.Constants;
import by.homesite.kpparser.utils.SaverTypes;
import com.google.gson.Gson;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alex on 9/16/17.
 */
@Component(Constants.JSON_OUTPUT_FORMAT)
public class JsonSaverStrategy implements SaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(JsonSaverStrategy.class);

   @Autowired
   private Configuration freemarkerConfig;

   @Value("${saveDescriptionsFolder}")
   private String saveDescriptionsFolder;

   @Value("${rescanExistingDescriptions}")
   private Boolean rescanExistingDescriptions;

   @Override
   public void doSave(Film item) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + item.getFileName() + Constants.JSON_OUTPUT_EXTENSION);
      if (isFileExists(item.getFileName())) {
         return;
      }

      try (Writer out = Files.newBufferedWriter(outputFileName, Charset.defaultCharset())) {

         Gson gson = new Gson();
         gson.toJson(item, out);

      } catch (IOException e) {
         log.error("Can't write output file {}", SaverTypes.TEXT.toString());
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

   private void saveImage(String img, String fileName) {
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
