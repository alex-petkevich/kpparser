package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alex on 5/1/17.
 */
public class KPParser implements Parser {
   private static final String SEARCH_URL = "https://www.kinopoisk.ru/index.php?kp_query=";
   private static final String FILM_INFO_URL = "https://www.kinopoisk.ru";

   private static final Logger log = LoggerFactory.getLogger(KPParser.class);

   public static final String CHARSET = "UTF-8";

   @Override
   public List<SearchResultItem> searchFilms(FileInfo fileInfo) {
      Document doc;
      try {
         doc = Jsoup.connect(SEARCH_URL + URLEncoder.encode(fileInfo.getTitle(), CHARSET)).get();
      } catch (IOException e) {
         log.error("Can't get search results");
         return null;
      }
      Elements blocks = doc.select(".element");
      if (blocks.size() > 0) {
         List<SearchResultItem> result = new ArrayList();

         for (Element block : blocks) {
            SearchResultItem item = new SearchResultItem();
            String year = block.select(".year").text();
            item.setYear(year);
            Element link = block.select(".name a").first();
            item.setTitle(link.text());
            item.setUrl(link.attr("data-url"));

            result.add(item);
         }

         return result;
      }

      return null;
   }

   @Override
   public Film parseFilmInfo(String url, FileInfo inputFile) {
      Document doc;
      Film film = new Film();

      if (!StringUtils.isEmpty(url)) {
         try {
            doc = Jsoup.connect(FILM_INFO_URL + url).get();
         } catch (IOException e) {
            log.error("Can't get film info");
            return null;
         }

         film.setFileName(inputFile.getName());
         film.setTitle(inputFile.getTitle());
         film.setYear(inputFile.getYear());
         film.setUrl(FILM_INFO_URL + url);
         String rating = doc.select(".rating_ball").first().text();
         film.setKpRating(rating);
         String description = doc.select(".film-synopsys").first().text();
         film.setDescription(description);
      }
      return film;
   }
}
