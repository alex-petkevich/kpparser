package by.homesite.kpparser.model;

/**
 * @author alex on 4/30/17.
 */
public class Film {
   private String title;
   private String year;
   private String description;
   private String fileName;
   private String url;
   private String kpRating;
   private String imdbRating;

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

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getFileName() {
      return fileName;
   }

   public void setFileName(String fileName) {
      this.fileName = fileName;
   }

   public String getUrl() {
      return url;
   }

   public void setUrl(String url) {
      this.url = url;
   }

   public String getKpRating() {
      return kpRating;
   }

   public void setKpRating(String kpRating) {
      this.kpRating = kpRating;
   }

   public String getImdbRating() {
      return imdbRating;
   }

   public void setImdbRating(String imdbRating) {
      this.imdbRating = imdbRating;
   }
}
