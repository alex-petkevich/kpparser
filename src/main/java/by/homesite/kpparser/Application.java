package by.homesite.kpparser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author alex on 5/1/17.
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@PropertySource("classpath:application.properties")
public class Application implements ApplicationRunner {

   private static final String SCAN_FILES_OPTION = "scanFilesFolder";
   private static final String HELP_OPTION = "help";
   
   @Value("${defaultSettings}")
   private boolean defaultSettings;

   public static void main(String... args) {
      SpringApplication app = new SpringApplication(Application.class);
      app.setBannerMode(Banner.Mode.OFF);
      app.run(args);
   }

   @Override
   public void run(ApplicationArguments args) {
      if (args.getOptionNames().contains(HELP_OPTION)) {
         displayCommandLineHelp();
      }

      if (!defaultSettings) {
         checkCommandLineArguments(args);
      }
   }
   
   private static void displayCommandLineHelp() {
      System.out.println("Usage:  java -jar kpparser.jar --scanFilesFolder=/path/to/movies/ [optional arguments]\n");
      System.out.println("Available arguments:");
      System.out.println("--scanFilesFolder:             folder to scan movie files (required) ");
      System.out.println("--locationPattern:             file mask to scan movie files (*.mov by default)");
      System.out.println("--inputSystem:                 comma separated readers list. Use first accessible reader ");
      System.out.println("                               possible values: KinoPoisk, rutor, imdb ");
      System.out.println("--outputFormat:                possible values TEXT, JSON, XML  (TEXT by default)");
      System.out.println("--saveDescriptionsFolder:      folder to save movie descriptions (scanFilesFolder bu default)");
      System.out.println("--useProxy:                    (true/false) use proxies list to scan (false by default)");
      System.out.println("--proxyListFile:               path to plain text file with proxies list ");
      System.out.println("--rescanExistingDescriptions:  (true/false) omit or scan again already existing descriptions  (false by default)");
      System.out.println("--blocksQty:                   how many items to process before save it to the output (3 by default) ");
      System.exit(0);
   }

   private static void checkCommandLineArguments(ApplicationArguments args) {
      if (!args.getOptionNames().contains(SCAN_FILES_OPTION)) {
         System.out.println("Please input folder to scan movies: java -jar kpparser.jar --scanFilesFolder=/path/to/movies/ [optional arguments]");
         System.exit(0);
      }

   }
}
