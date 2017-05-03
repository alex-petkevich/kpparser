package by.homesite.kpparser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author alex on 5/1/17.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class Application {

   public static void main(String[] args) throws Exception {
      SpringApplication.run(Application.class, args);
   }
}
