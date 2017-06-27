package by.homesite.kpparser.writers;

import by.homesite.kpparser.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author alex on 5/3/17.
 */
public class TextFileItemWriter implements ItemWriter<Film> {
   private Resource resource;
   private static final Logger log = LoggerFactory.getLogger(TextFileItemWriter.class);

   @Override
   public void write(List<? extends Film> items) throws Exception {

      items.forEach(item -> log.info("Parsed data for: ", item.getTitle()));
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }
}
