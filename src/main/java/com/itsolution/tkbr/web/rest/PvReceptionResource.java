package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.PvReception;

import com.itsolution.tkbr.repository.PvReceptionRepository;
import com.itsolution.tkbr.repository.search.PvReceptionSearchRepository;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PvReception.
 */
@RestController
@RequestMapping("/api")
public class PvReceptionResource {

    private final Logger log = LoggerFactory.getLogger(PvReceptionResource.class);

    private static final String ENTITY_NAME = "pvReception";
        
    private final PvReceptionRepository pvReceptionRepository;

    private final PvReceptionSearchRepository pvReceptionSearchRepository;

    public PvReceptionResource(PvReceptionRepository pvReceptionRepository, PvReceptionSearchRepository pvReceptionSearchRepository) {
        this.pvReceptionRepository = pvReceptionRepository;
        this.pvReceptionSearchRepository = pvReceptionSearchRepository;
    }

    /**
     * POST  /pv-receptions : Create a new pvReception.
     *
     * @param pvReception the pvReception to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pvReception, or with status 400 (Bad Request) if the pvReception has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pv-receptions")
    @Timed
    public ResponseEntity<PvReception> createPvReception(@Valid @RequestBody PvReception pvReception) throws URISyntaxException {
        log.debug("REST request to save PvReception : {}", pvReception);
        if (pvReception.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pvReception cannot already have an ID")).body(null);
        }
        PvReception result = pvReceptionRepository.save(pvReception);
        pvReceptionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pv-receptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pv-receptions : Updates an existing pvReception.
     *
     * @param pvReception the pvReception to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pvReception,
     * or with status 400 (Bad Request) if the pvReception is not valid,
     * or with status 500 (Internal Server Error) if the pvReception couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pv-receptions")
    @Timed
    public ResponseEntity<PvReception> updatePvReception(@Valid @RequestBody PvReception pvReception) throws URISyntaxException {
        log.debug("REST request to update PvReception : {}", pvReception);
        if (pvReception.getId() == null) {
            return createPvReception(pvReception);
        }
        PvReception result = pvReceptionRepository.save(pvReception);
        pvReceptionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pvReception.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pv-receptions : get all the pvReceptions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pvReceptions in body
     */
    @GetMapping("/pv-receptions")
    @Timed
    public ResponseEntity<List<PvReception>> getAllPvReceptions(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PvReceptions");
        Page<PvReception> page = pvReceptionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pv-receptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pv-receptions/:id : get the "id" pvReception.
     *
     * @param id the id of the pvReception to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pvReception, or with status 404 (Not Found)
     */
    @GetMapping("/pv-receptions/{id}")
    @Timed
    public ResponseEntity<PvReception> getPvReception(@PathVariable Long id) {
        log.debug("REST request to get PvReception : {}", id);
        PvReception pvReception = pvReceptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(pvReception));
    }

    /**
     * DELETE  /pv-receptions/:id : delete the "id" pvReception.
     *
     * @param id the id of the pvReception to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pv-receptions/{id}")
    @Timed
    public ResponseEntity<Void> deletePvReception(@PathVariable Long id) {
        log.debug("REST request to delete PvReception : {}", id);
        pvReceptionRepository.delete(id);
        pvReceptionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/pv-receptions?query=:query : search for the pvReception corresponding
     * to the query.
     *
     * @param query the query of the pvReception search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/pv-receptions")
    @Timed
    public ResponseEntity<List<PvReception>> searchPvReceptions(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of PvReceptions for query {}", query);
        Page<PvReception> page = pvReceptionSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/pv-receptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
