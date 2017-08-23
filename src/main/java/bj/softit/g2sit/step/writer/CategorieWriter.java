package bj.softit.g2sit.step.writer;

import bj.softit.g2sit.domain.Categorie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.util.List;

public class CategorieWriter implements ItemWriter<Categorie> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public DataSource dataSource;

    @Override
    public void write(List<? extends Categorie> messages) throws Exception {
        /*for(String msg : messages){
            System.out.println("#Writer Step: " + msg);
        }*/
    }

   /* @Bean
    public JdbcBatchItemWriter<Categorie> writer() {
        JdbcBatchItemWriter<Categorie> writer = new JdbcBatchItemWriter<Categorie>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Categorie>());
        writer.setSql("INSERT INTO categorie (name_categorie, fournisseur, description) VALUES (:name_categorie, :fournisseur, :description)");
        writer.setDataSource(dataSource);
        return writer;
    }*/

}
