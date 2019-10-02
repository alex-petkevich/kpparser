package by.homesite.kpparser.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author alex on 4/30/17.
 */
@XmlRootElement
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
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

}
