package bj.softit.g2sit.service;

import bj.softit.g2sit.domain.Historiques;
import bj.softit.g2sit.domain.OutStock;
import bj.softit.g2sit.domain.Produits;
import bj.softit.g2sit.domain.Stock;
import bj.softit.g2sit.repository.HistoriquesRepository;
import bj.softit.g2sit.repository.ProduitsRepository;
import bj.softit.g2sit.repository.search.HistoriquesSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Historiques.
 */
@Service
@Transactional
public class HistoriquesService {

    private final Logger log = LoggerFactory.getLogger(HistoriquesService.class);

    private final HistoriquesRepository historiquesRepository;
    private final ProduitsRepository produitsRepository;
    private final HistoriquesSearchRepository historiquesSearchRepository;
    private final UserService userService;


    public HistoriquesService(HistoriquesRepository historiquesRepository, ProduitsRepository produitsRepository, HistoriquesSearchRepository historiquesSearchRepository, UserService userService) {
        this.historiquesRepository = historiquesRepository;
        this.produitsRepository = produitsRepository;
        this.historiquesSearchRepository = historiquesSearchRepository;
        this.userService = userService;
    }

    /**
     * Save a historiques.
     *
     * @param historiques the entity to save
     * @return the persisted entity
     */
    public Historiques save(Historiques historiques) {
        log.debug("Request to save Historiques : {}", historiques);
        Historiques result = historiquesRepository.save(historiques.user(userService.getUserWithAuthorities()));
        historiquesSearchRepository.save(result);
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
        return historiquesRepository.findAllByOrderByDateDesc(pageable);
    }

    public List<Historiques> findAllByOrderByDate() {
        log.debug("Request to get all Historiques");
        return historiquesRepository.findAllByOrderByDateDesc();
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

    public void addHistOut(OutStock outStock) {

        historiquesRepository.save(new Historiques().operation("Sortir de stock")
            .date(ZonedDateTime.now()).user(userService.getUserWithAuthorities()).outs(outStock));
        Produits produits = produitsRepository.findOne(outStock.getProduit().getId());
        //BigDecimal a= outStock.getQuantite().negate();
        produits.getStock().setQuantite(produits.getStock().getQuantite().add(outStock.getQuantite().negate()));
    }

    public void addHistEnter(Stock stock) {
        historiquesRepository.save(new Historiques()
            .date(ZonedDateTime.now())
            .operation("Entrer de stock")
            .user(userService.getUserWithAuthorities())
            .stocks(stock));
        Produits produits = produitsRepository.findOne(stock.getProduit().getId());
        //BigDecimal a= outStock.getQuantite().negate();
        // produits.getStock().setQuantite(produits.getStock().getQuantite().add(stock.getQuantite()));
    }
    public void updateHistEnter(Stock stock) {
        historiquesRepository.save(new Historiques()
            .date(ZonedDateTime.now())
            .operation("Mise a jour de stock")
            .user(userService.getUserWithAuthorities())
            .stocks(stock));
        Produits produits = produitsRepository.findOne(stock.getProduit().getId());
        //BigDecimal a= outStock.getQuantite().negate();
        // produits.getStock().setQuantite(produits.getStock().getQuantite().add(stock.getQuantite()));
    }

    public void addHist(String s) {
        historiquesRepository.save(new Historiques().date(ZonedDateTime.now()).operation(s).user(userService.getUserWithAuthorities()));
    }

    public Page<Historiques> findByDatesBetween(ZonedDateTime fromDate, ZonedDateTime toDate, Pageable pageable) {
    return historiquesRepository.findAllByDateBetween(fromDate,toDate,pageable);
    }

    public Page<Historiques> findByDatesBetweenAndProduit(ZonedDateTime fromDate, ZonedDateTime toDate, long id, Pageable pageable) {
    return historiquesRepository.findAllByDateBetweenAndStocks_Produit_IdOrOuts_Produit_Id(fromDate,toDate,id,id,pageable);
    }

    /*public Page<Historiques> findByDatesBetween(Instant fromDate, Instant toDate, Pageable pageable) {
        return historiquesSearchRepository.findAllByDateBetween(fromDate, toDate, pageable);
    }
    */
}
