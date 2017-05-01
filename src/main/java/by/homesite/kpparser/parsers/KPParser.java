package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.processors.FilmProcessor;
import by.homesite.kpparser.processors.Parser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * @author alex on 5/1/17.
 */
public class KPParser implements Parser {
   private static final String SEARCH_URL = "https://plus.kinopoisk.ru/search/?text=";
   private static final String FILM_INFO_URL = "https://plus.kinopoisk.ru/film/";

   private static final Logger log = LoggerFactory.getLogger(KPParser.class);

   @Override
   public List<SearchResultItem> searchFilms(FileInfo fileInfo) {
      Document doc = null;
      try {
         doc = Jsoup.connect(SEARCH_URL + fileInfo.getTitle()).get();
      } catch (IOException e) {
         log.error("Can't get search results");
         return null;
      }
      Elements blocks = doc.select(".film-snippet__content");

      return null;
   }

   @Override
   public Film parseFilmInfo(String url) {
      return null;
   }
}
