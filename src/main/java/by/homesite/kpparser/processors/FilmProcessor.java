package by.homesite.kpparser.processors;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.parsers.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alex on 4/30/17.
 */
@Component
public class FilmProcessor implements ItemProcessor<FileInfo, Film> {
   private static final Logger log = LoggerFactory.getLogger(FilmProcessor.class);
   private static final int TTW_BETWEEN_REQUESTS = 3000;

   @Value("${inputSystem}")
   private String inputSystem;

   @Autowired
   private
   Map<String, Parser> parsers = new HashMap<>();

   @Override
   public Film process(final FileInfo inputFile) throws Exception {

      log.info("Converting {}...", inputFile.getName());
      Film filmInfo = null;

      if (!parsers.containsKey(inputSystem)) {
         return null;
      }

      Parser infoParser = parsers.get(inputSystem);

      List<SearchResultItem> items = infoParser.searchFilms(inputFile);
      if (items != null && items.size() > 0)
      {
         SearchResultItem listItem;

         if (!StringUtils.isEmpty(inputFile.getYear())) {
             listItem = items.stream()
                  .filter(item -> !StringUtils.isEmpty(item.getYear()) && item.getYear().equals(inputFile.getYear()))
                  .findFirst()
                  .orElse(null);

         } else {
            listItem = items.get(0);
         }

         if (listItem != null && !StringUtils.isEmpty(listItem.getUrl())) {
            Thread.sleep(TTW_BETWEEN_REQUESTS);
            filmInfo = infoParser.parseFilmInfo(listItem, inputFile);
         }
      }

      return filmInfo;
   }

}
