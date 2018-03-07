package by.homesite.kpparser.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.Calendar;
import java.util.stream.IntStream;

/**
 * @author alex on 6/27/17.
 */
public class FilenameUtils {
   private static final int[] yearsRange = IntStream.range(1900, Calendar.getInstance().get(Calendar.YEAR) + 3).toArray();

   private FilenameUtils() {

   }

   public static String extractTitleFromFilename(String name) {
      String title = null; 
      StringBuilder result = new StringBuilder();
      String[] words = prepareTitle(name);
      for(String word: words) {
         if (isProperYear(word) && title == null) {
             title = result.toString().trim();
         }
         result.append(word).append(" ");
      }
      if (title == null) {
         title = "";
      }
      
      if (title.indexOf("[") > 0) {
          title = title.substring(title.indexOf("[") + 1, title.indexOf("]"));
      }
      
      return title.trim();
   }

   public static String extractYearFromFilename(String name) {
      String[] words = prepareTitle(name);
      for(String word: words) {
         final String year = (word.length() == 4) ? word.replace('O', '0') : word;
         if (isProperYear(year)) {
            return year.trim();
         }
      }

      return "";
   }

   public static String cleanHtml(String text) {
      return Jsoup.clean(text, Whitelist.none());
   }

   private static boolean isProperYear(String str)
   {
      return str.matches("-?\\d+(\\.\\d+)?") && IntStream.of(yearsRange).anyMatch(x -> String.valueOf(x).equals(str));
   }

   private static String[] prepareTitle(String name) {
      //crop extension
      int extPos = name.lastIndexOf('.');
      if (extPos > 0)
         name = name.substring(0, extPos);
      //replace all dots by spaces
      name = name.replaceAll("[._\\-()|]+", " ");
      //merge words if it is not a year
      return name.split("\\s+");
   }
}
