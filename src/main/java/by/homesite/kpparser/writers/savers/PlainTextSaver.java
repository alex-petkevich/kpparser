package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alex on 6/29/17.
 */
public class PlainTextSaver implements Saver {
   private static final Logger log = LoggerFactory.getLogger(PlainTextSaver.class);

   @Autowired
   private Configuration freemarkerConfig;

   @Value("${saveDescriptionsFolder}")
   private String saveDescriptionsFolder;

   @Override
   public void doSave(Film item) {
      Map root = new HashMap<String, Object>();
      root.put("film", item);
      String outputFileName = saveDescriptionsFolder + item.getFileName() + ".txt";

      try (Writer out = new BufferedWriter(new FileWriter(outputFileName))) {

         Template template = freemarkerConfig.getTemplate(SaverTypes.TEXT.toString() +".ftlh");
         template.process(root, out);

      } catch (IOException e) {
         log.error("Can't read output template for {}", SaverTypes.TEXT.toString());
      } catch (TemplateException e) {
         log.error("Can't process output template for {}", SaverTypes.TEXT.toString());
      }

   }
}
