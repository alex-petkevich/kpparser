package by.homesite.kpparser.net;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author alex on 7/9/17.
 */
@Service
public class HttpClient {

   @Autowired
   private IProxy proxy;
   private Map<String, String> cookies = new HashMap<>();
   private static final Logger log = LoggerFactory.getLogger(HttpClient.class);

   public Document get(String url) {
      Document doc;
      try {
         Connection connection = Jsoup
               .connect(url)
               .cookies(cookies)
               .proxy(proxy.getProxy());
         doc = connection.get();
         cookies = connection.response().cookies();
      } catch (IOException e) {
         log.error("Can't get url content for {}", url);
         return null;
      }
      return doc;
   }

}
