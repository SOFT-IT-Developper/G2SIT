package bj.softit.g2sit.config;
import bj.softit.g2sit.batch.JpaQueryProviderImpl;
import bj.softit.g2sit.domain.Categorie;
import bj.softit.g2sit.domain.Stock;
import bj.softit.g2sit.step.processor.CategorieProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
//import org.springframework.oxm.xstream.XStreamMarshaller;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


//@Configuration
public class BatchConfig2 {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;


    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public DataSource dataSource;

    private static final Logger log = LoggerFactory.getLogger(BatchConfig2.class);

    @Bean
    public Job job() {
        return jobBuilderFactory.get("job")
            .incrementer(new RunIdIncrementer())
            .flow(step1())
            .end()
            .build();
    }
    /* @Bean
    public Job job2() {
        return jobBuilderFactory.get("job")
            .incrementer(new RunIdIncrementer())
            .flow(step2())
            .end()
            .build();
    }*/

//    @Bean
//    public Step step2() {
//        return stepBuilderFactory.get("step2")
//            .<Stock, Stock> chunk(1)
//            .reader(new StockReader())
//            .processor(new StockProcessor())
//            .writer(Writer2())
//            .build();
//    }
    // configuration xml
    /*@Bean
    public Job readStock() throws Exception {
        return jobBuilderFactory.get("readStock").flow(step1()).end().build();
    }
*/
   /* @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").<Stock, Stock>chunk(10).reader(reader()).writer(writer())
            .build();
    }*/
    @Bean
    public Step step10() {
        return stepBuilderFactory.get("step2").<Stock, Stock>chunk(10).reader(reader()).writer(writer2())
            .build();
    }

    @Bean
    public JpaPagingItemReader<Stock> reader() {
        JpaPagingItemReader<Stock> databaseReader = new JpaPagingItemReader<>();
        databaseReader.setEntityManagerFactory(entityManagerFactory);
        JpaQueryProviderImpl<Stock> jpaQueryProvider = new JpaQueryProviderImpl<>();
        jpaQueryProvider.setQuery("Stock.findAll");
        databaseReader.setQueryProvider(jpaQueryProvider);
        databaseReader.setPageSize(1000);
        try {
            databaseReader.afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return databaseReader;
    }

    @Bean
    public StaxEventItemWriter<Stock> writer() {
        StaxEventItemWriter<Stock> writer = new StaxEventItemWriter<>();
        writer.setResource(new FileSystemResource("Output/Stock.xml"));
        writer.setMarshaller(StockUnmarshaller());
        writer.setRootTagName("Stocks");
        return writer;
    }

    @Bean
    public XStreamMarshaller StockUnmarshaller()  {
        XStreamMarshaller unMarshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<String, Class>();
        aliases.put("Stock", Stock.class);
        try {
            unMarshaller.setAliases(aliases);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return unMarshaller;
    }
    @Bean
    public FlatFileItemWriter<Stock> writer2(){
        FlatFileItemWriter<Stock> writer =new FlatFileItemWriter<Stock>();
        writer.setResource(new FileSystemResource("Output/Stock.csv"));
        writer.setLineAggregator(new DelimitedLineAggregator<Stock>(){{
            setDelimiter(",");
           /* setFieldExtractor(new BeanWrapperFieldExtractor<Stock>(){{
                setNames(new String[]{"id","nom"});
            }});*/
        }});
        return writer;
    }

    @Bean
    public FlatFileItemReader<Categorie> readerCat() {
        FlatFileItemReader<Categorie> reader = new FlatFileItemReader<Categorie>();
        reader.setResource(new  FileSystemResource("categorie.csv"));
        reader.setLineMapper(new DefaultLineMapper<Categorie>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
               //setDelimiter(",");
                setNames(new String[] {"nameCategorie", "fournisseur","description" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Categorie>() {{
                setTargetType(Categorie.class);
            }});
        }});
        log.info("reader 1 ("+reader+") into ("+reader+")");

        return reader;
    }
    @Bean
    public JdbcBatchItemWriter<Categorie> writerCat() {
        JdbcBatchItemWriter<Categorie> writer = new JdbcBatchItemWriter<Categorie>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Categorie>());
        writer.setSql("INSERT INTO categorie (name_categorie, fournisseur, description) VALUES (:nameCategorie, :fournisseur, :description)");
        writer.setDataSource(dataSource);
        return writer;
    }
    @Bean
    public Job importUserJob() {
        System.out.println("cool 1");
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .flow(step1())
            .end()
            .build();

    }
    /* @Bean
    public Job importUserJob(JobCompletionNotificationListener listener) {
        return jobBuilderFactory.get("importUserJob")
            .incrementer(new RunIdIncrementer())
            .listener(listener)
            .flow(step1())
            .end()
            .build();
    }*/
    @Bean
    public Step step1() {
        System.out.println("cool 2");
        return stepBuilderFactory.get("step1")
            .<Categorie, Categorie> chunk(10)
            .reader(readerCat())
            .processor(processor())
            .writer(writerCat())
            .build();
    }

    @Bean
    public CategorieProcessor processor() {
        System.out.println("cool 3");
        return new CategorieProcessor();
    }
}
