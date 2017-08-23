package bj.softit.g2sit.step;

import bj.softit.g2sit.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;


public class StockReader implements ItemReader<Stock>{

    private String[] messages = {"Hello World!", "Welcome to Spring Batch!"};

    private int count=0;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Stock read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        return null;
    }

    // @Override
  /*  public Stock read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {


        return Stock;
    }*/

}
