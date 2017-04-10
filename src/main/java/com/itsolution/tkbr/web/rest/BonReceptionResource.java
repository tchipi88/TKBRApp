package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.BonReception;

import com.itsolution.tkbr.repository.BonReceptionRepository;
import com.itsolution.tkbr.repository.search.BonReceptionSearchRepository;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing BonReception.
 */
@RestController
@RequestMapping("/api")
public class BonReceptionResource {

    private final Logger log = LoggerFactory.getLogger(BonReceptionResource.class);

    private static final String ENTITY_NAME = "bonReception";
        
    private final BonReceptionRepository bonReceptionRepository;

    private final BonReceptionSearchRepository bonReceptionSearchRepository;

    public BonReceptionResource(BonReceptionRepository bonReceptionRepository, BonReceptionSearchRepository bonReceptionSearchRepository) {
        this.bonReceptionRepository = bonReceptionRepository;
        this.bonReceptionSearchRepository = bonReceptionSearchRepository;
    }

    /**
     * POST  /bon-receptions : Create a new bonReception.
     *
     * @param bonReception the bonReception to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonReception, or with status 400 (Bad Request) if the bonReception has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-receptions")
    @Timed
    public ResponseEntity<BonReception> createBonReception(@Valid @RequestBody BonReception bonReception) throws URISyntaxException {
        log.debug("REST request to save BonReception : {}", bonReception);
        if (bonReception.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonReception cannot already have an ID")).body(null);
        }
        BonReception result = bonReceptionRepository.save(bonReception);
        bonReceptionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bon-receptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-receptions : Updates an existing bonReception.
     *
     * @param bonReception the bonReception to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonReception,
     * or with status 400 (Bad Request) if the bonReception is not valid,
     * or with status 500 (Internal Server Error) if the bonReception couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-receptions")
    @Timed
    public ResponseEntity<BonReception> updateBonReception(@Valid @RequestBody BonReception bonReception) throws URISyntaxException {
        log.debug("REST request to update BonReception : {}", bonReception);
        if (bonReception.getId() == null) {
            return createBonReception(bonReception);
        }
        BonReception result = bonReceptionRepository.save(bonReception);
        bonReceptionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonReception.getId().toString()))
            .body(result);
    }

   /**
     * GET  /bon-receptions : get all the bonReceptions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonReceptions in body
     */
    @GetMapping("/bon-receptions")
    @Timed
    public ResponseEntity<List<BonReception>> getAllBonReceptions(@ApiParam Pageable pageable) {
        log.debug("REST request to get all BonReceptions");
        Page<BonReception> page = bonReceptionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bon-receptions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bon-receptions/:id : get the "id" bonReception.
     *
     * @param id the id of the bonReception to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonReception, or with status 404 (Not Found)
     */
    @GetMapping("/bon-receptions/{id}")
    @Timed
    public ResponseEntity<BonReception> getBonReception(@PathVariable Long id) {
        log.debug("REST request to get BonReception : {}", id);
        BonReception bonReception = bonReceptionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonReception));
    }

    /**
     * DELETE  /bon-receptions/:id : delete the "id" bonReception.
     *
     * @param id the id of the bonReception to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-receptions/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonReception(@PathVariable Long id) {
        log.debug("REST request to delete BonReception : {}", id);
        bonReceptionRepository.delete(id);
        bonReceptionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bon-receptions?query=:query : search for the bonReception corresponding
     * to the query.
     *
     * @param query the query of the bonReception search 
     * @return the result of the search
     */
    @GetMapping("/_search/bon-receptions")
    @Timed
    public List<BonReception> searchBonReceptions(@RequestParam String query) {
        log.debug("REST request to search BonReceptions for query {}", query);
        return StreamSupport
            .stream(bonReceptionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
