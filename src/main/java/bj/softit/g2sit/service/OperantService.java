package bj.softit.g2sit.service;

import bj.softit.g2sit.domain.Operant;
import bj.softit.g2sit.repository.OperantRepository;
import bj.softit.g2sit.repository.search.OperantSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Operant.
 */
@Service
@Transactional
public class OperantService {

    private final Logger log = LoggerFactory.getLogger(OperantService.class);

    private final OperantRepository operantRepository;

    private final OperantSearchRepository operantSearchRepository;

    public OperantService(OperantRepository operantRepository, OperantSearchRepository operantSearchRepository) {
        this.operantRepository = operantRepository;
        this.operantSearchRepository = operantSearchRepository;
    }

    /**
     * Save a operant.
     *
     * @param operant the entity to save
     * @return the persisted entity
     */
    public Operant save(Operant operant) {
        log.debug("Request to save Operant : {}", operant);
        Operant result = operantRepository.save(operant);
        operantSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the operants.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operant> findAll(Pageable pageable) {
        log.debug("Request to get all Operants");
        return operantRepository.findAll(pageable);
    }

    /**
     *  Get one operant by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Operant findOne(Long id) {
        log.debug("Request to get Operant : {}", id);
        return operantRepository.findOne(id);
    }

    /**
     *  Delete the  operant by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Operant : {}", id);
        operantRepository.delete(id);
        operantSearchRepository.delete(id);
    }

    /**
     * Search for the operant corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Operant> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operants for query {}", query);
        Page<Operant> result = operantSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
