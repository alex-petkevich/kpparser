package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.config.SaverTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author alex on 6/29/17.
 */
@Component
public class ContextExecutor {
   private static final Logger log = LoggerFactory.getLogger(ContextExecutor.class);

   @Autowired
   Map<String, SaverStrategy> saverStrategies = new HashMap<>();

   public void save(Film item, SaverTypes type) {
      log.info("Save data for: {}", item.getTitle());

      SaverStrategy saver = saverStrategies.get(type.toString());

      if (saver != null)
         saver.doSave(item);
   }

   public boolean isFileExists(String fileName, SaverTypes type) {

      SaverStrategy saver = saverStrategies.get(type.toString());

      if (saver != null)
         return saver.isFileExists(fileName);

      return false;
   }
}
