package by.homesite.kpparser.config;

import java.nio.charset.StandardCharsets;

/**
 * @author alex on 7/2/17.
 */
public class Constants {

   // outputs
   public static final String TEXT_OUTPUT_FORMAT = "TEXT";
   public static final String TEXT_OUTPUT_EXTENSION = ".txt";
   public static final String JSON_OUTPUT_FORMAT = "JSON";
   public static final String JSON_OUTPUT_EXTENSION = ".json";
   public static final String XML_OUTPUT_FORMAT = "XML";
   public static final String XML_OUTPUT_EXTENSION = ".xml";
   public static final String FREEMARKER_TEMPLATES_EXTENSIONS = ".ftlh";

   // inputs
   public static final String INPUT_SYSTEMS_KINOPOISK = "KinoPoisk";
   public static final String INPUT_SYSTEMS_RUTOR = "rutor";
   public static final String INPUT_SYSTEMS_IMDB = "imdb";

   // general
   public static final String CHARSET = StandardCharsets.UTF_8.toString();
   public static final String TEMPLATES = "/templates/";

   private Constants() {

   }
}
