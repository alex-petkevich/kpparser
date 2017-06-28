package by.homesite.kpparser.parsers;

import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.model.SearchResultItem;

import java.util.List;

/**
 * @author alex on 4/30/17.
 */
public interface Parser {

   List<SearchResultItem> searchFilms(FileInfo fileInfo);

   Film parseFilmInfo(SearchResultItem url, FileInfo inputFile);
}
