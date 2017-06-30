package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static by.homesite.kpparser.writers.savers.SaverTypes.TEXT;

/**
 * @author alex on 6/29/17.
 */
public class ContextExecutor {
   private static final Logger log = LoggerFactory.getLogger(ContextExecutor.class);

   public void save(Film item, SaverTypes type) {
      log.info("Save data for: {}", item.getTitle());

      Saver saver = null;

      switch (type) {
         case TEXT:
            saver = new PlainTextSaver();
      }

      if (saver != null)
         saver.doSave(item);
   }
}
