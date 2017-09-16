package by.homesite.kpparser.writers.savers;

import by.homesite.kpparser.model.Film;

/**
 * @author alex on 6/29/17.
 */
public interface SaverStrategy {
   void doSave(Film item);

   boolean isFileExists(String fileName);
}
