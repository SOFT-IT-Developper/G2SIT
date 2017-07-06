package bj.softit.g2sit.service;

import bj.softit.g2sit.domain.Historiques;
import bj.softit.g2sit.repository.HistoriquesRepository;
import bj.softit.g2sit.repository.search.HistoriquesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Historiques.
 */
@Service
@Transactional
public class HistoriquesService {

    private final Logger log = LoggerFactory.getLogger(HistoriquesService.class);

    private final HistoriquesRepository historiquesRepository;

    private final HistoriquesSearchRepository historiquesSearchRepository;

    public HistoriquesService(HistoriquesRepository historiquesRepository, HistoriquesSearchRepository historiquesSearchRepository) {
        this.historiquesRepository = historiquesRepository;
        this.historiquesSearchRepository = historiquesSearchRepository;
    }

    /**
     * Save a historiques.
     *
     * @param historiques the entity to save
     * @return the persisted entity
     */
    public Historiques save(Historiques historiques) {
        log.debug("Request to save Historiques : {}", historiques);
        Historiques result = historiquesRepository.save(historiques);
        historiquesSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the historiques.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Historiques> findAll(Pageable pageable) {
        log.debug("Request to get all Historiques");
        return historiquesRepository.findAll(pageable);
    }

    /**
     *  Get one historiques by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Historiques findOne(Long id) {
        log.debug("Request to get Historiques : {}", id);
        return historiquesRepository.findOne(id);
    }

    /**
     *  Delete the  historiques by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Historiques : {}", id);
        historiquesRepository.delete(id);
        historiquesSearchRepository.delete(id);
    }

    /**
     * Search for the historiques corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Historiques> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Historiques for query {}", query);
        Page<Historiques> result = historiquesSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
