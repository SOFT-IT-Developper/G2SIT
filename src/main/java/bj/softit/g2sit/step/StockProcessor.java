package bj.softit.g2sit.step;

import bj.softit.g2sit.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StockProcessor implements ItemProcessor<Stock, Stock> {
    private final Logger log = LoggerFactory.getLogger(StockProcessor.class);
    @Override
    public Stock process(Stock stock) throws Exception {
        log.debug("Lecture procesus stock");
        return stock;
    }
}
