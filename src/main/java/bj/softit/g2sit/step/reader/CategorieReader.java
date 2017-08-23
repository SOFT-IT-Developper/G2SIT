package bj.softit.g2sit.step.reader;

import bj.softit.g2sit.domain.Categorie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


public class CategorieReader implements ItemReader<Categorie>{

    private String[] messages = {"Hello World!", "Welcome to Spring Batch!"};

    private int count=0;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Categorie read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
/*
        if(count < messages.length){
            return messages[count++];
        }else{
            count=0;
        }*/
        return null;
    }

}
