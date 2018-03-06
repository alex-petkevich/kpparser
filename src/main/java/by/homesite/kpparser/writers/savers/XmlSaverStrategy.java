package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.config.Constants;
import by.homesite.kpparser.config.SaverTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author alex on 9/16/17.
 */
@Component(Constants.XML_OUTPUT_FORMAT)
public class XmlSaverStrategy extends AbstractSaverStrategy implements SaverStrategy {
   private static final Logger log = LoggerFactory.getLogger(XmlSaverStrategy.class);

   @Value("${rescanExistingDescriptions}")
   private Boolean rescanExistingDescriptions;

   @Override
   public void doSave(Film item) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + item.getFileName() + Constants.XML_OUTPUT_EXTENSION);
      if (isFileExists(item.getFileName())) {
         return;
      }

      try (Writer out = Files.newBufferedWriter(outputFileName, Charset.defaultCharset())) {

         StringWriter sw = new StringWriter();
         JAXBContext context = JAXBContext.newInstance(Film.class);
         Marshaller m = context.createMarshaller();
         m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
         m.marshal(item, sw);
         out.write(sw.toString());

      } catch (IOException e) {
         log.error("Can't write output file {}", SaverTypes.XML.toString());
      } catch (JAXBException e) {
         log.error("Can't parse to xml {}",item.getFileName());
      }

      if (!StringUtils.isEmpty(item.getImg())) {
         saveImage(item.getImg(), item.getFileName());
      }
   }

   @Override
   public boolean isFileExists(String fileName) {
      Path outputFileName = Paths.get(saveDescriptionsFolder + fileName + Constants.XML_OUTPUT_EXTENSION);
      return  (Files.exists(outputFileName) && Boolean.TRUE != rescanExistingDescriptions);
   }

}
