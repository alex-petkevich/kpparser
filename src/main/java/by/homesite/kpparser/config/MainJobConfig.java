package by.homesite.kpparser.config;

import by.homesite.kpparser.listener.JobCompletionNotificationListener;
import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.processors.FilmProcessor;
import by.homesite.kpparser.readers.FilenameItemReader;
import by.homesite.kpparser.writers.FileItemWriter;
import by.homesite.kpparser.writers.savers.ContextExecutor;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * @author alex on 5/1/17.
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableBatchProcessing
public class MainJobConfig implements ResourceLoaderAware {

   @Autowired
   public JobBuilderFactory jobBuilderFactory;

   @Autowired
   public StepBuilderFactory stepBuilderFactory;

   @Value("${scanFilesFolder}")
   private String scanFilesFolder;

   @Value("${locaitonPattern}")
   private String locaitonPattern;

   private ResourceLoader resourceLoader;

   // tag::readerwriterprocessor[]
   @Bean
   public MultiResourceItemReader multiResourceItemReader() throws IOException {

      MultiResourceItemReader reader = new MultiResourceItemReader();
      reader.setResources(new PathMatchingResourcePatternResolver().getResources(scanFilesFolder + locaitonPattern));

      reader.setDelegate(new FilenameItemReader());

      return reader;
   }

   @Bean
   public FilmProcessor processor() {
      return new FilmProcessor();
   }

   @Bean
   public FileItemWriter writer() {
      return new FileItemWriter();
   }
   // end::readerwriterprocessor[]

   // tag::jobstep[]
   @Bean
   public Job importUserJob(JobCompletionNotificationListener listener) throws IOException {
      return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1())
            .end()
            .build();
   }

   @Bean
   public Step step1() throws IOException {
      return stepBuilderFactory.get("step1")
            .<FileInfo, Film> chunk(10)
            .reader(multiResourceItemReader())
            .processor(processor())
            .writer(writer())
            .build();
   }

   @Override
   public void setResourceLoader(ResourceLoader resourceLoader) {
      this.resourceLoader = resourceLoader;
   }
   // end::jobstep[]

   @Bean(name ="freemarkerConfig")
   public freemarker.template.Configuration freemarkerConfig() throws IOException {
      freemarker.template.Configuration configurer = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_26);
      configurer.setClassForTemplateLoading(this.getClass(), "/templates");
      configurer.setDefaultEncoding("UTF-8");
      configurer.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
      configurer.setLogTemplateExceptions(false);
      return configurer;
   }

}
