package by.homesite.kpparser.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author alex on 4/30/17.
 */
@Getter
@Setter
@EqualsAndHashCode
public class FileInfo {
   private String name;
   private String title;
   private String year;
   private String translatedTitle;

}
