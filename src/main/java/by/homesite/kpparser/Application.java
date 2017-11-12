package by.homesite.kpparser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringUtils;

/**
 * @author alex on 5/1/17.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@PropertySource("classpath:application.properties")
public class Application {

   @Value("${defaultSettings}")
   private static boolean defaultSettings;

   public static void main(String[] args) throws Exception {

      if (!defaultSettings) {
         checkCommandLineArguments();
      }
      SpringApplication.run(Application.class, args);
   }

   private static void checkCommandLineArguments() {
      String scanFilesFolder = System.getProperty("scanFilesFolder");

      if (StringUtils.isEmpty(scanFilesFolder)) {
         System.out.println("Please input folder to scan movies: java -jar kpparser.jar --scanFilesFolder=/path/to/movies/ [optional arguments]");
         System.exit(0);
      }

   }
}
