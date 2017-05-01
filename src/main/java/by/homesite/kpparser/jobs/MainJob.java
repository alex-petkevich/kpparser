package by.homesite.kpparser.config;

import by.homesite.kpparser.listener.JobCompletionNotificationListener;
import by.homesite.kpparser.model.FileInfo;
import by.homesite.kpparser.model.Film;
import by.homesite.kpparser.processors.FilmProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.ResourcesItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author alex on 5/1/17.
 */
@Configuration
@EnableBatchProcessing
public class MainJob {

   @Autowired
   public JobBuilderFactory jobBuilderFactory;

   @Autowired
   public StepBuilderFactory stepBuilderFactory;

   // tag::readerwriterprocessor[]
   @Bean
   public ResourcesItemReader<FileInfo> reader() {
      ResourcesItemReader<FileInfo> reader = new ResourcesItemReader<>();
      reader.setResource(new ClassPathResource("sample-data.csv"));
      reader.setLineMapper(new DefaultLineMapper<FileInfo>() {{
         setLineTokenizer(new DelimitedLineTokenizer() {{
            setNames(new String[] { "firstName", "lastName" });
         }});
         setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
            setTargetType(Person.class);
         }});
      }});
      return reader;
   }

   @Bean
   public FilmProcessor processor() {
      return new FilmProcessor();
   }

   @Bean
   public JdbcBatchItemWriter<Film> writer() {
      JdbcBatchItemWriter<Film> writer = new JdbcBatchItemWriter<Film>();
      return writer;
   }
   // end::readerwriterprocessor[]

   // tag::jobstep[]
   @Bean
   public Job importUserJob(JobCompletionNotificationListener listener) {
      return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1())
            .end()
            .build();
   }

   @Bean
   public Step step1() {
      return stepBuilderFactory.get("step1")
            .<FileInfo, Film> chunk(10)
            .reader(reader())
            .processor(processor())
            .writer(writer())
            .build();
   }
   // end::jobstep[]
}
