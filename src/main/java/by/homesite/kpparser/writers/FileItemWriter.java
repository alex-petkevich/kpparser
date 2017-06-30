package by.homesite.kpparser.writers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.writers.savers.ContextExecutor;
import by.homesite.kpparser.writers.savers.Saver;
import by.homesite.kpparser.writers.savers.SaverTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author alex on 5/3/17.
 */
public class FileItemWriter implements ItemWriter<Film> {
   private Resource resource;

   @Autowired
   private ContextExecutor contextExecutor;

   @Value("${outputFormat}")
   private String outputFormat;

   @Override
   public void write(List<? extends Film> items) throws Exception {

      items.forEach(item -> contextExecutor.save(item, SaverTypes.valueOf(outputFormat)));
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }
}
