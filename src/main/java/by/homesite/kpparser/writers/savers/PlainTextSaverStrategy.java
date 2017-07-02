package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.utils.Constants;
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
 * @author alex on 6/29/17.
 */
@Component(Constants.TEXT_OUTPUT_FORMAT)
public class PlainTextSaverStrategy implements SaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(PlainTextSaverStrategy.class);

   @Autowired
   private Configuration freemarkerConfig;

   @Value("${saveDescriptionsFolder}")
   private String saveDescriptionsFolder;

   @Override
   public void doSave(Film item) {
      Map root = new HashMap<String, Object>();
      root.put("film", item);
      Path outputFileName = Paths.get(saveDescriptionsFolder + item.getFileName() + Constants.TEXT_OUTPUT_EXTENSION);

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

   private void saveImage(String img, String fileName) {
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