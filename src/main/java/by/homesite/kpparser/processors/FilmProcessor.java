package by.homesite.kpparser.processors;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;
import by.homesite.kpparser.parsers.Parser;
import by.homesite.kpparser.writers.FileItemWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
   private FileItemWriter fileItemWriter;

   @Autowired
   private
   Map<String, Parser> parsers = new HashMap<>();

   @Override
   public Film process(final FileInfo inputFile) throws Exception {

      log.info("Converting {}...", inputFile.getName());

      if (fileItemWriter.isFileExists(inputFile.getName())) {
         return null;
      }

      Film filmInfo = null;

      if (inputSystem == null) {
         return null;
      }
      List<String> inputParsers = Arrays.stream(inputSystem.split(","))
              .map(String::trim)
              .collect(Collectors.toList());

      for (String input: inputParsers) {
         filmInfo = parseSingleSystem(input, inputFile);
         if (filmInfo != null && !StringUtils.isEmpty(filmInfo.getDescription())) {
            return filmInfo;
         }
      }

      return filmInfo;
   }

   private Film parseSingleSystem(final String input, final FileInfo inputFile) throws Exception {

     Film filmInfo = null;

      if (!parsers.containsKey(input)) {
         return null;
      }

      Parser infoParser = parsers.get(input);

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
