package by.homesite.kpparser.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @author alex on 6/27/17.
 */
public class FilenameUtils {
   private static int[] yearsRange = IntStream.range(1930, 2020).toArray();

   private FilenameUtils() {

   }

   public static String extractTitleFromFilename(String name) {
      StringBuilder result = new StringBuilder();
      String[] words = prepareTitle(name);
      for(String word: words) {
         if (isNumeric(word) && IntStream.of(yearsRange).anyMatch(x -> String.valueOf(x).equals(word))) {
            return result.toString().trim();
         }
         result.append(word).append(" ");
      }

      return result.toString().trim();
   }

   public static String extractYearFromFilename(String name) {
      String[] words = prepareTitle(name);
      for(String word: words) {
         if (isNumeric(word) && IntStream.of(yearsRange).anyMatch(x -> String.valueOf(x).equals(word))) {
            return word.trim();
         }
      }

      return "";
   }

   private static boolean isNumeric(String str)
   {
      return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
   }

   private static String[] prepareTitle(String name) {
      //crop extension
      name = name.substring(0, name.lastIndexOf('.'));
      //replace all dots by spaces
      name = name.replaceAll("[\\._\\-\\(\\)]+", " ");
      //merge words if it is not a year
      return name.split("\\s+");
   }
}
