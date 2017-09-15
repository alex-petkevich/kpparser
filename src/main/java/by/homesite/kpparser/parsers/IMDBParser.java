package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.net.HttpClient;
import by.homesite.kpparser.utils.Constants;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static by.homesite.kpparser.utils.Constants.CHARSET;
import static by.homesite.kpparser.utils.FilenameUtils.cleanHtml;

/**
 * @author alex on 9/10/17.
 */
@Component(Constants.INPUT_SYSTEMS_IMDB)
public class IMDBParser implements Parser {
   private static final String SEARCH_URL = "http://www.imdb.com/find?&s=tt&q=";
   private static final String FILM_INFO_URL = "http://www.imdb.com";

   private static final Logger log = LoggerFactory.getLogger(IMDBParser.class);

   private static final String TD_COUNTRY = "Country:";
   private static final String TD_GENRES = "Genres:";
   private static final String TD_DIRECTOR = "Director:";
   private static final String TD_ROLES = "Stars:";

   @Autowired
   private HttpClient httpClient;

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
      Elements blocks = doc.select(".result_text");
      if (blocks.size() > 0) {
         List<SearchResultItem> result = new ArrayList();

         for (Element block : blocks) {
            SearchResultItem item = new SearchResultItem();
            String blockContent = block.text();
            item.setYear(findYear(blockContent));
            Element link = block.select("a").first();
            item.setTitle(link.text());
            item.setUrl(FILM_INFO_URL + link.attr("href"));

            result.add(item);
         }

         return result;
      }

      return null;
   }

   private String findYear(String blockContent) {
      if (StringUtils.isEmpty(blockContent)) {
         return "";
      }
      int startBracket = blockContent.indexOf("(");
      int endBracket = blockContent.indexOf(")");
      return blockContent.substring(startBracket + 1, endBracket);
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

         Element img = doc.select(".poster img").first();
         if (img != null)
            film.setImg(extractBigImg(img.attr("src")));

         film.setCountry(extractTag(doc, ".txt-block", TD_COUNTRY));
         film.setDirector(extractTag(doc, ".credit_summary_item", TD_DIRECTOR));
         film.setGenre(extractTag(doc, ".see-more", TD_GENRES));
         film.setRoles(extractTag(doc, ".credit_summary_item", TD_ROLES));

         film.setDescription(doc.select("div[itemprop=description]").first().text());

      }
      return film;
   }

   private String extractBigImg(String src) {
      // BUG
      return src.replaceFirst(".+@(.*)\\.", "");
   }

   private String extractTag(Document doc, String selector, String tag) {
      Elements info = doc.select(selector);
      for (Element element: info) {
         Element blockName = element.select("h4").first();

         if (blockName != null && tag.equalsIgnoreCase(blockName.text())) {
            Elements countries = element.select("a");
            //TODO: comma separated
            return cleanHtml(countries.text());
         }

      }
      return "";
   }
}
