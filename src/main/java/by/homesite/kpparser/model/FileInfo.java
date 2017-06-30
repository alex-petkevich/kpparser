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

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      FileInfo fileInfo = (FileInfo) o;

      if (name != null ? !name.equals(fileInfo.name) : fileInfo.name != null) return false;
      if (title != null ? !title.equals(fileInfo.title) : fileInfo.title != null) return false;
      if (year != null ? !year.equals(fileInfo.year) : fileInfo.year != null) return false;
      return translatedTitle != null ? translatedTitle.equals(fileInfo.translatedTitle) : fileInfo.translatedTitle == null;
   }

   @Override
   public int hashCode() {
      int result = name != null ? name.hashCode() : 0;
      result = 31 * result + (title != null ? title.hashCode() : 0);
      result = 31 * result + (year != null ? year.hashCode() : 0);
      result = 31 * result + (translatedTitle != null ? translatedTitle.hashCode() : 0);
      return result;
   }
}
