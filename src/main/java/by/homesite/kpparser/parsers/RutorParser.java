package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.net.HttpClient;
import by.homesite.kpparser.config.Constants;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static by.homesite.kpparser.config.Constants.CHARSET;
import static by.homesite.kpparser.utils.FilenameUtils.cleanHtml;
import static by.homesite.kpparser.utils.FilenameUtils.extractYearFromFilename;

/**
 * @author alex on 7/4/17.
 */
@Component(Constants.INPUT_SYSTEMS_RUTOR)
public class RutorParser implements Parser {

   private static final String SEARCH_URL = "http://rutor.is/search/";
   private static final String FILM_INFO_URL = "http://rutor.is";

   private static final Logger log = LoggerFactory.getLogger(RutorParser.class);

   private static final String TD_COUNTRY = "Страна:";
   private static final String TD_PRODUCED = "Производство:";
   private static final String TD_GENRES = "Жанр:";
   private static final String TD_DIRECTOR = "Режиссер:";
   private static final String TD_ROLES = "В ролях:";
   private static final String TD_ABOUT = "Описание:";
   private static final String TD_ABOUT_EXT = "О фильме:";
   private static final String TD_TITLE = "Название:";
   private static final String TD_ORIG_TITLE = "Оригинальное название";
   private static final String TD_YEAR = "Год выпуска:";

   private final HttpClient httpClient;

   @Autowired
   public RutorParser(HttpClient httpClient) {
      this.httpClient = httpClient;
   }

   @Override
   public List<SearchResultItem> searchFilms(FileInfo fileInfo) {

      Document doc;
      try {
         doc = httpClient.get(SEARCH_URL + URLEncoder.encode(fileInfo.getTitle(), CHARSET));
      } catch (IOException e) {
         log.error("Can't get search results for {}", fileInfo.getName());
         return null;
      }
      if (doc == null) {
         return null;
      }

      Elements blocks = doc.select(".gai,.tum");
      if (blocks.size() > 0) {
         List<SearchResultItem> result = new ArrayList<>();

         for (Element block : blocks) {
            SearchResultItem item = new SearchResultItem();
            if (block.select("td").size() < 2) {
               continue;
            }

            Element td = block.select("td").get(1);
            Element a = td.select("a").last();

            item.setTitle(a.text());
            item.setUrl(FILM_INFO_URL + a.attr("href"));
            item.setYear(extractYearFromFilename(item.getTitle()));

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
      film.setFileName(inputFile.getName());
      film.setYear(inputFile.getYear());

      if (!StringUtils.isEmpty(searchItem.getUrl())) {
         film.setUrl(searchItem.getUrl());
         film.setTitle(searchItem.getTitle());

         doc = httpClient.get(searchItem.getUrl());

         if (doc == null) {
            return film;
         }

         Element infoTr = doc.select("#details tr").first();
         if (infoTr == null ) {
            return film;
         }
         Element info = infoTr.select("td").last();

         Element img = info.select("img").first();
         if (img != null)
            film.setImg(img.attr("src"));

         String lines[] = info.html().split("<br\\s*/?>");
         Pattern stringData = Pattern.compile("<b>(.+?)</b>(.*)");

         String about_ext = "";
         String produced = "";
         for(String line: lines) {
            Matcher m = stringData.matcher(line);
            if (m.find()) {
               if (TD_COUNTRY.equalsIgnoreCase(m.group(1)))
                  film.setCountry(m.group(2));
               if (TD_PRODUCED.equalsIgnoreCase(m.group(1)))
                  produced = m.group(2);
               if (TD_GENRES.equalsIgnoreCase(m.group(1)))
                  film.setGenre(cleanHtml(m.group(2)));
               if (TD_DIRECTOR.equalsIgnoreCase(m.group(1)))
                  film.setDirector(m.group(2));
               if (TD_ROLES.equalsIgnoreCase(m.group(1)))
                  film.setRoles(m.group(2));
               if (TD_ABOUT.equalsIgnoreCase(m.group(1)))
                  film.setDescription(cleanHtml(m.group(2)));
               if (TD_ABOUT_EXT.equalsIgnoreCase(m.group(1)))
                  about_ext = cleanHtml(m.group(2));
               if (TD_TITLE.equalsIgnoreCase(m.group(1)))
                  film.setTitle(m.group(2));
               if (TD_ORIG_TITLE.equalsIgnoreCase(m.group(1)))
                  film.setOriginalTitle(m.group(2));
               if (TD_YEAR.equalsIgnoreCase(m.group(1)))
                  film.setYear(cleanHtml(m.group(2)));
            }
         }
         if (StringUtils.isEmpty(film.getDescription()))
            film.setDescription(about_ext);
         if (StringUtils.isEmpty(film.getCountry()))
            film.setCountry(cleanHtml(produced));
         if (StringUtils.isEmpty(film.getRoles())) {
            int foundLine = 0;
            for(String line: lines) {
               if (foundLine == 2) {
                  film.setRoles(cleanHtml(line));
                  break;
               }
               if (foundLine == 1)
                  foundLine++;
               Matcher m = stringData.matcher(line);
               if (m.find() && TD_ROLES.equalsIgnoreCase(m.group(1))) {
                  foundLine = 1;
               }
            }
         }


      }
      return film;
   }

}
