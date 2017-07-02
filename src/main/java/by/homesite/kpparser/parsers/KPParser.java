package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;

import by.homesite.kpparser.utils.Constants;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

/**
 * @author alex on 5/1/17.
 */
@Component(Constants.INPUT_SYSTEMS_KINOPOISK)
public class KPParser implements Parser {
   public static final String CHARSET = "UTF-8";

   private static final String SEARCH_URL = "https://www.kinopoisk.ru/index.php?kp_query=";
   private static final String FILM_INFO_URL = "https://www.kinopoisk.ru";

   private static final Logger log = LoggerFactory.getLogger(KPParser.class);

   private static final String TD_COUNTRY = "страна";
   private static final String TD_GENRES = "жанр";
   private static final String TD_DIRECTOR = "режиссер";

   @Override
   public List<SearchResultItem> searchFilms(FileInfo fileInfo) {
      Document doc;
      try {
         String url = SEARCH_URL + URLEncoder.encode(fileInfo.getTitle(), CHARSET);
         doc = Jsoup.connect(url).get();
      } catch (IOException e) {
         log.error("Can't get search results for {}", fileInfo.getName());
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
   public Film parseFilmInfo(SearchResultItem searchItem, FileInfo inputFile) {
      Document doc;
      Film film = new Film();

      if (!StringUtils.isEmpty(searchItem.getUrl())) {
         try {
            doc = Jsoup.connect(FILM_INFO_URL + searchItem.getUrl()).get();
         } catch (IOException e) {
            log.error("Can't get film {} info for {}", searchItem.getTitle(), FILM_INFO_URL + searchItem.getUrl());
            return null;
         }

         film.setImg(doc.select(".popupBigImage img").first().attr("src"));
         Elements info = doc.select(".info td");
         ListIterator iter = info.listIterator();
         Element previous = null;
         if (iter.hasNext())
         {
            previous = (Element) iter.next();
         }

         while(iter.hasNext() && previous != null) {
            Element current = (Element) iter.next();

            if (TD_COUNTRY.equals(previous.text()))
               film.setCountry(current.text());
            if (TD_GENRES.equals(previous.text()))
               film.setGenre(current.text());
            if (TD_DIRECTOR.equals(previous.text()))
               film.setDirector(current.text());

            previous = current;
         }

         Element rolesList = doc.select("#actorList ul").first();
         Elements roles = rolesList.select("li a");
         film.setRoles(roles.stream().map(Element::text).collect( Collectors.joining(", ")));

         film.setTitle(doc.select(".moviename-big").first().text());
         film.setOriginalTitle(doc.select("span[itemprop=alternativeHeadline]").first().text());
         film.setKpRating(doc.select(".rating_ball").first().text());
         film.setDescription(doc.select(".film-synopsys").first().text());

         film.setFileName(inputFile.getName());
         film.setYear(inputFile.getYear());
         film.setUrl(FILM_INFO_URL + searchItem.getUrl());

      }
      return film;
   }
}
