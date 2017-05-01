package by.homesite.kpparser.model;

/**
 * @author alex on 4/30/17.
 */
public class FileInfo {
   private String name;
   private String title;
   private String year;
   private String translatedTitle;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getYear() {
      return year;
   }

   public void setYear(String year) {
      this.year = year;
   }

   public String getTranslatedTitle() {
      return translatedTitle;
   }

   public void setTranslatedTitle(String translatedTitle) {
      this.translatedTitle = translatedTitle;
   }
}
