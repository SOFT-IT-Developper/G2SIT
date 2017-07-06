package bj.softit.g2sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import bj.softit.g2sit.domain.Operant;
import bj.softit.g2sit.service.OperantService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Operant.
 */
@RestController
@RequestMapping("/api")
public class OperantResource {

    private final Logger log = LoggerFactory.getLogger(OperantResource.class);

    private static final String ENTITY_NAME = "operant";

    private final OperantService operantService;

    public OperantResource(OperantService operantService) {
        this.operantService = operantService;
    }

    /**
     * POST  /operants : Create a new operant.
     *
     * @param operant the operant to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operant, or with status 400 (Bad Request) if the operant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operants")
    @Timed
    public ResponseEntity<Operant> createOperant(@RequestBody Operant operant) throws URISyntaxException {
        log.debug("REST request to save Operant : {}", operant);
        if (operant.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new operant cannot already have an ID")).body(null);
        }
        Operant result = operantService.save(operant);
        return ResponseEntity.created(new URI("/api/operants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operants : Updates an existing operant.
     *
     * @param operant the operant to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operant,
     * or with status 400 (Bad Request) if the operant is not valid,
     * or with status 500 (Internal Server Error) if the operant couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operants")
    @Timed
    public ResponseEntity<Operant> updateOperant(@RequestBody Operant operant) throws URISyntaxException {
        log.debug("REST request to update Operant : {}", operant);
        if (operant.getId() == null) {
            return createOperant(operant);
        }
        Operant result = operantService.save(operant);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, operant.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operants : get all the operants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operants in body
     */
    @GetMapping("/operants")
    @Timed
    public ResponseEntity<List<Operant>> getAllOperants(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Operants");
        Page<Operant> page = operantService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operants/:id : get the "id" operant.
     *
     * @param id the id of the operant to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operant, or with status 404 (Not Found)
     */
    @GetMapping("/operants/{id}")
    @Timed
    public ResponseEntity<Operant> getOperant(@PathVariable Long id) {
        log.debug("REST request to get Operant : {}", id);
        Operant operant = operantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(operant));
    }

    /**
     * DELETE  /operants/:id : delete the "id" operant.
     *
     * @param id the id of the operant to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operants/{id}")
    @Timed
    public ResponseEntity<Void> deleteOperant(@PathVariable Long id) {
        log.debug("REST request to delete Operant : {}", id);
        operantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/operants?query=:query : search for the operant corresponding
     * to the query.
     *
     * @param query the query of the operant search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/operants")
    @Timed
    public ResponseEntity<List<Operant>> searchOperants(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Operants for query {}", query);
        Page<Operant> page = operantService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/operants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
