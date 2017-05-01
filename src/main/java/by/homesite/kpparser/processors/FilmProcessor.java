package by.homesite.kpparser.processors;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

/**
 * @author alex on 4/30/17.
 */
public class FilmProcessor implements ItemProcessor<FileInfo, Film> {
   private static final Logger log = LoggerFactory.getLogger(FilmProcessor.class);

   @Override
   public Film process(final FileInfo inputFile) throws Exception {

      log.info("Converting");

      return null;
   }

}
