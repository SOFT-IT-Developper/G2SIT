package bj.softit.g2sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.softit.g2sit.domain.Historiques;
import bj.softit.g2sit.service.HistoriquesService;
import bj.softit.g2sit.web.rest.util.HeaderUtil;
import bj.softit.g2sit.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Historiques.
 */
@RestController
@RequestMapping("/api")
public class HistoriquesResource {

    private final Logger log = LoggerFactory.getLogger(HistoriquesResource.class);

    private static final String ENTITY_NAME = "historiques";

    private final HistoriquesService historiquesService;

    public HistoriquesResource(HistoriquesService historiquesService) {
        this.historiquesService = historiquesService;
    }

    /**
     * POST  /historiques : Create a new historiques.
     *
     * @param historiques the historiques to create
     * @return the ResponseEntity with status 201 (Created) and with body the new historiques, or with status 400 (Bad Request) if the historiques has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/historiques")
    @Timed
    public ResponseEntity<Historiques> createHistoriques(@Valid @RequestBody Historiques historiques) throws URISyntaxException {
        log.debug("REST request to save Historiques : {}", historiques);
        if (historiques.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new historiques cannot already have an ID")).body(null);
        }
        Historiques result = historiquesService.save(historiques);
        return ResponseEntity.created(new URI("/api/historiques/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /historiques : Updates an existing historiques.
     *
     * @param historiques the historiques to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated historiques,
     * or with status 400 (Bad Request) if the historiques is not valid,
     * or with status 500 (Internal Server Error) if the historiques couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/historiques")
    @Timed
    public ResponseEntity<Historiques> updateHistoriques(@Valid @RequestBody Historiques historiques) throws URISyntaxException {
        log.debug("REST request to update Historiques : {}", historiques);
        if (historiques.getId() == null) {
            return createHistoriques(historiques);
        }
        Historiques result = historiquesService.save(historiques);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, historiques.getId().toString()))
            .body(result);
    }

    /**
     * GET  /historiques : get all the historiques.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of historiques in body
     */
    @GetMapping("/historiques")
    @Timed
    public ResponseEntity<List<Historiques>> getAllHistoriques(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Historiques");
        Page<Historiques> page = historiquesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/historiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /historiques/:id : get the "id" historiques.
     *
     * @param id the id of the historiques to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the historiques, or with status 404 (Not Found)
     */
    @GetMapping("/historiques/{id}")
    @Timed
    public ResponseEntity<Historiques> getHistoriques(@PathVariable Long id) {
        log.debug("REST request to get Historiques : {}", id);
        Historiques historiques = historiquesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(historiques));
    }

    /**
     * DELETE  /historiques/:id : delete the "id" historiques.
     *
     * @param id the id of the historiques to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/historiques/{id}")
    @Timed
    public ResponseEntity<Void> deleteHistoriques(@PathVariable Long id) {
        log.debug("REST request to delete Historiques : {}", id);
        historiquesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/historiques?query=:query : search for the historiques corresponding
     * to the query.
     *
     * @param query the query of the historiques search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/historiques")
    @Timed
    public ResponseEntity<List<Historiques>> searchHistoriques(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Historiques for query {}", query);
        Page<Historiques> page = historiquesService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/historiques");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
