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
   private String country;
   private String director;
   private String roles;
   private String genre;
   private String img;
   private String originalTitle;

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

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getDirector() {
      return director;
   }

   public void setDirector(String director) {
      this.director = director;
   }

   public String getRoles() {
      return roles;
   }

   public void setRoles(String roles) {
      this.roles = roles;
   }

   public String getGenre() {
      return genre;
   }

   public void setGenre(String genre) {
      this.genre = genre;
   }

   public String getImg() {
      return img;
   }

   public void setImg(String img) {
      this.img = img;
   }

   public String getOriginalTitle() {
      return originalTitle;
   }

   public void setOriginalTitle(String originalTitle) {
      this.originalTitle = originalTitle;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      Film film = (Film) o;

      if (!title.equals(film.title)) return false;
      if (year != null ? !year.equals(film.year) : film.year != null) return false;
      if (description != null ? !description.equals(film.description) : film.description != null) return false;
      if (fileName != null ? !fileName.equals(film.fileName) : film.fileName != null) return false;
      if (url != null ? !url.equals(film.url) : film.url != null) return false;
      if (kpRating != null ? !kpRating.equals(film.kpRating) : film.kpRating != null) return false;
      if (imdbRating != null ? !imdbRating.equals(film.imdbRating) : film.imdbRating != null) return false;
      if (country != null ? !country.equals(film.country) : film.country != null) return false;
      if (director != null ? !director.equals(film.director) : film.director != null) return false;
      if (roles != null ? !roles.equals(film.roles) : film.roles != null) return false;
      if (genre != null ? !genre.equals(film.genre) : film.genre != null) return false;
      return img != null ? img.equals(film.img) : film.img == null;
   }

   @Override
   public int hashCode() {
      int result = title.hashCode();
      result = 31 * result + (year != null ? year.hashCode() : 0);
      result = 31 * result + (description != null ? description.hashCode() : 0);
      result = 31 * result + (fileName != null ? fileName.hashCode() : 0);
      result = 31 * result + (url != null ? url.hashCode() : 0);
      result = 31 * result + (kpRating != null ? kpRating.hashCode() : 0);
      result = 31 * result + (imdbRating != null ? imdbRating.hashCode() : 0);
      result = 31 * result + (country != null ? country.hashCode() : 0);
      result = 31 * result + (director != null ? director.hashCode() : 0);
      result = 31 * result + (roles != null ? roles.hashCode() : 0);
      result = 31 * result + (genre != null ? genre.hashCode() : 0);
      result = 31 * result + (img != null ? img.hashCode() : 0);
      return result;
   }
}
