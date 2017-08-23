package bj.softit.g2sit.batch;

import bj.softit.g2sit.config.DatabaseConfiguration;
import bj.softit.g2sit.domain.Stock;
import bj.softit.g2sit.domain.User;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import java.sql.DriverManager;
import java.sql.ResultSet;



public class BacthConfiguration {

  /*  @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job readUser() throws Exception {
        return jobBuilderFactory.get("readUser").flow(step1()).end().build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1").<User, User>chunk(10).reader(reader()).writer(writer())
            .build();
    }

    @Bean
    public JpaPagingItemReader<User> reader() throws Exception {
        JpaPagingItemReader<User> databaseReader = new JpaPagingItemReader<>();
        databaseReader.setEntityManagerFactory(entityManagerFactory);
        JpaQueryProviderImpl<User> jpaQueryProvider = new JpaQueryProviderImpl<>();
        jpaQueryProvider.setQuery("User.findAll");
        databaseReader.setQueryProvider(jpaQueryProvider);
        databaseReader.setPageSize(1000);
        databaseReader.afterPropertiesSet();
        return databaseReader;
    }

    @Bean
    public StaxEventItemWriter<User> writer() {
        StaxEventItemWriter<User> writer = new StaxEventItemWriter<>();
        writer.setResource(new FileSystemResource("xml/user.xml"));
        writer.setMarshaller(userUnmarshaller());
        writer.setRootTagName("users");
        return writer;
    }

    @Bean
    public XStreamMarshaller userUnmarshaller() {
        XStreamMarshaller unMarshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<String, Class>();
        aliases.put("user", User.class);
        unMarshaller.setAliases(aliases);
        return unMarshaller;
    }*/
  /*@Autowired
  private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job(@Qualifier("step1") Step step1, @Qualifier("step2") Step step2) {
        return jobs.get("myJob").start(step1).next(step2).build();
    }

    @Bean
    protected Step step1(ItemReader<Stock> reader, ItemProcessor<Stock, Stock> processor, ItemWriter<Stock> writer) {
        return steps.get("step1")
            .<Stock, Stock> chunk(10)
            .reader(reader)
            .processor(processor)
            .writer(writer)
            .build();
    }

    @Bean
    protected Step step2(Tasklet tasklet) {
        return steps.get("step2")
            .tasklet(tasklet)
            .build();
    }
    @Bean
    public JdbcCursorItemReader<Stock> reader(){
        JdbcCursorItemReader<Stock> reader = new JdbcCursorItemReader<Stock>();
        reader.setSql('Select s from stock');
        reader.setRowMapper(rowMapper);
        return reader;
    }
    public class StockRowMapper implements RowMapper<Stock>{
        @Override
        public Stock mapRow(ResultSet rs, int rowCol){
            Stock stock=new Stock();

            return stock;
        }

        *//**
         * Returns the rows that the TreePath instances in <code>path</code>
         * are being displayed at. The receiver should return an array of
         * the same length as that passed in, and if one of the TreePaths
         * in <code>path</code> is not valid its entry in the array should
         * be set to -1.
         *
         * @param path
         *//*
        @Override
        public int[] getRowsForPaths(TreePath[] path) {
            return new int[0];
        }
    }
*/
}
