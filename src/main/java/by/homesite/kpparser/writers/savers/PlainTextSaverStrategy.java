package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.config.Constants;
import by.homesite.kpparser.config.SaverTypes;
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
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alex on 6/29/17.
 */
@Component(Constants.TEXT_OUTPUT_FORMAT)
public class PlainTextSaverStrategy extends AbstractSaverStrategy implements SaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(PlainTextSaverStrategy.class);

   @Autowired
   private Configuration freemarkerConfig;

   @Value("${rescanExistingDescriptions}")
   private Boolean rescanExistingDescriptions;

   @Override
   public void doSave(Film item) {
      Map root = new HashMap<String, Object>();
      root.put("film", item);
      Path outputFileName = Paths.get(saveDescriptionsFolder + item.getFileName() + Constants.TEXT_OUTPUT_EXTENSION);
      if (isFileExists(item.getFileName())) {
         return;
      }

      try (Writer out = Files.newBufferedWriter(outputFileName, Charset.defaultCharset())) {

         Template template = freemarkerConfig.getTemplate(SaverTypes.TEXT.toString() + Constants.FREEMARKER_TEMPLATES_EXTENSIONS);
         template.process(root, out);

      } catch (IOException e) {
         log.error("Can't write output file {}", SaverTypes.TEXT.toString());
      } catch (TemplateException e) {
         log.error("Can't process output template for {}", SaverTypes.TEXT.toString());
      }

      if (!StringUtils.isEmpty(item.getImg())) {
         saveImage(item.getImg(), item.getFileName());
      }
   }

   @Override
   public boolean isFileExists(String fileName) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + fileName + Constants.TEXT_OUTPUT_EXTENSION);
      return  (Files.exists(outputFileName) && Boolean.TRUE != rescanExistingDescriptions);
   }
}
