package by.homesite.kpparser.processors;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.parsers.KPParser;
import by.homesite.kpparser.parsers.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author alex on 4/30/17.
 */
public class FilmProcessor implements ItemProcessor<FileInfo, Film> {
   private static final Logger log = LoggerFactory.getLogger(FilmProcessor.class);

   @Override
   public Film process(final FileInfo inputFile) throws Exception {

      log.info("Converting ", inputFile.getName());
      Film filmInfo = null;

      // TODO: inject parser to avoid of hardcode
      Parser infoParser = new KPParser();

      List<SearchResultItem> items = infoParser.searchFilms(inputFile);
      String url = "";
      if (items != null && items.size() > 0)
      {
         if (!StringUtils.isEmpty(inputFile.getYear())) {
            for (SearchResultItem item : items) {
               if (!StringUtils.isEmpty(item.getYear()) && item.getYear().equals(inputFile.getYear())) {
                  url = item.getUrl();
               }
            }
         } else {
            url = items.get(0).getUrl();
         }
      }
      if (!StringUtils.isEmpty(url)) {
         filmInfo = infoParser.parseFilmInfo(url, inputFile);
      }

      return filmInfo;
   }

}
