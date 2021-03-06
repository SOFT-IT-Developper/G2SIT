package bj.softit.g2sit.service;

import bj.softit.g2sit.domain.Stock;
import bj.softit.g2sit.repository.StockRepository;
import bj.softit.g2sit.repository.search.StockSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Stock.
 */
@Service
@Transactional
public class StockService {

    private final Logger log = LoggerFactory.getLogger(StockService.class);

    private final StockRepository stockRepository;

    private final StockSearchRepository stockSearchRepository;

    public StockService(StockRepository stockRepository, StockSearchRepository stockSearchRepository) {
        this.stockRepository = stockRepository;
        this.stockSearchRepository = stockSearchRepository;
    }

    /**
     * Save a stock.
     *
     * @param stock the entity to save
     * @return the persisted entity
     */
    public Stock save(Stock stock) {
        log.debug("Request to save Stock : {}", stock);
        Stock result = stockRepository.save(stock);
        stockSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the stocks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Stock> findAll(Pageable pageable) {
        log.debug("Request to get all Stocks");
        return stockRepository.findAll(pageable);
    }

    /**
     *  Get one stock by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Stock findOne(Long id) {
        log.debug("Request to get Stock : {}", id);
        return stockRepository.findOne(id);
    }

    /**
     *  Delete the  stock by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Stock : {}", id);
        stockRepository.delete(id);
        stockSearchRepository.delete(id);
    }

    /**
     * Search for the stock corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Stock> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Stocks for query {}", query);
        Page<Stock> result = stockSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
//custome

    public Stock findByProduitId(Long id) {
        log.debug("Request to get Stock produit id : {}", id);
        return stockRepository.findByProduit_Id(id);
    }

    public List<Stock> findByBetwenToDate(ZonedDateTime d1, ZonedDateTime d2) {
       return stockRepository.findByDateentrerBetween(d1,d2);
    }
    public long countAll(){
        return stockRepository.countAllByQuantiteIsAfter(BigDecimal.valueOf(5));
    }
    public long countAllManquant(){
        return stockRepository.countAllByQuantiteIsBefore(BigDecimal.valueOf(5));
    }
}
