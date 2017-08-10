package bj.softit.g2sit.service;

import bj.softit.g2sit.domain.OutStock;
import bj.softit.g2sit.repository.OutStockRepository;
import bj.softit.g2sit.repository.search.OutStockSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.Instant;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing OutStock.
 */
@Service
@Transactional
public class OutStockService {

    private final Logger log = LoggerFactory.getLogger(OutStockService.class);

    private final OutStockRepository outStockRepository;

    private final OutStockSearchRepository outStockSearchRepository;

    public OutStockService(OutStockRepository outStockRepository, OutStockSearchRepository outStockSearchRepository) {
        this.outStockRepository = outStockRepository;
        this.outStockSearchRepository = outStockSearchRepository;
    }

    /**
     * Save a outStock.
     *
     * @param outStock the entity to save
     * @return the persisted entity
     */
    public OutStock save(OutStock outStock) {
        log.debug("Request to save OutStock : {}", outStock);
        OutStock result = outStockRepository.save(outStock);
        outStockSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the outStocks.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OutStock> findAll(Pageable pageable) {
        log.debug("Request to get all OutStocks");
        return outStockRepository.findAll(pageable);
    }

    /**
     *  Get one outStock by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public OutStock findOne(Long id) {
        log.debug("Request to get OutStock : {}", id);
        return outStockRepository.findOne(id);
    }

    /**
     *  Delete the  outStock by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OutStock : {}", id);
        outStockRepository.delete(id);
        outStockSearchRepository.delete(id);
    }

    /**
     * Search for the outStock corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<OutStock> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of OutStocks for query {}", query);
        Page<OutStock> result = outStockSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }

    //custom

    public Page<OutStock> findByDates(Instant fromDate, Instant toDate, Pageable pageable) {
      return   outStockSearchRepository.findAllByDatesortirBetween(fromDate, toDate, pageable);
    }
 /*   public List<OutStock> convertToAuditEvent(Iterable<PersistentAuditEvent> persistentAuditEvents) {
        if (persistentAuditEvents == null) {
            return Collections.emptyList();
        }
        List<OutStock> auditEvents = new ArrayList<>();
        for (PersistentAuditEvent persistentAuditEvent : persistentAuditEvents) {
            auditEvents.add(convertToAuditEvent(persistentAuditEvent));
        }
        return auditEvents;
    }*/
 public long countAll(){
     return outStockRepository.count();
 }
}
