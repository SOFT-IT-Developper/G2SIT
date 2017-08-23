package bj.softit.g2sit.step.processor;

import bj.softit.g2sit.domain.Categorie;
import bj.softit.g2sit.domain.Stock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CategorieProcessor implements ItemProcessor<Categorie, Categorie> {
    private final Logger log = LoggerFactory.getLogger(CategorieProcessor.class);
    @Override
    public Categorie process(Categorie categorie) throws Exception {
        log.debug("Lecture procesus Categorie {} ",categorie);
        return categorie;
    }
}
