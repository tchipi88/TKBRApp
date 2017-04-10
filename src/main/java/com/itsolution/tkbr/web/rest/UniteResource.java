package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Unite;

import com.itsolution.tkbr.repository.UniteRepository;
import com.itsolution.tkbr.repository.search.UniteSearchRepository;
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
 * REST controller for managing Unite.
 */
@RestController
@RequestMapping("/api")
public class UniteResource {

    private final Logger log = LoggerFactory.getLogger(UniteResource.class);

    private static final String ENTITY_NAME = "unite";
        
    private final UniteRepository uniteRepository;

    private final UniteSearchRepository uniteSearchRepository;

    public UniteResource(UniteRepository uniteRepository, UniteSearchRepository uniteSearchRepository) {
        this.uniteRepository = uniteRepository;
        this.uniteSearchRepository = uniteSearchRepository;
    }

    /**
     * POST  /unites : Create a new unite.
     *
     * @param unite the unite to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unite, or with status 400 (Bad Request) if the unite has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unites")
    @Timed
    public ResponseEntity<Unite> createUnite(@Valid @RequestBody Unite unite) throws URISyntaxException {
        log.debug("REST request to save Unite : {}", unite);
        if (unite.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new unite cannot already have an ID")).body(null);
        }
        Unite result = uniteRepository.save(unite);
        uniteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/unites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unites : Updates an existing unite.
     *
     * @param unite the unite to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unite,
     * or with status 400 (Bad Request) if the unite is not valid,
     * or with status 500 (Internal Server Error) if the unite couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unites")
    @Timed
    public ResponseEntity<Unite> updateUnite(@Valid @RequestBody Unite unite) throws URISyntaxException {
        log.debug("REST request to update Unite : {}", unite);
        if (unite.getId() == null) {
            return createUnite(unite);
        }
        Unite result = uniteRepository.save(unite);
        uniteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unite.getId().toString()))
            .body(result);
    }

   /**
     * GET  /unites : get all the unites.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unites in body
     */
    @GetMapping("/unites")
    @Timed
    public ResponseEntity<List<Unite>> getAllUnites(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Unites");
        Page<Unite> page = uniteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/unites");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /unites/:id : get the "id" unite.
     *
     * @param id the id of the unite to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unite, or with status 404 (Not Found)
     */
    @GetMapping("/unites/{id}")
    @Timed
    public ResponseEntity<Unite> getUnite(@PathVariable Long id) {
        log.debug("REST request to get Unite : {}", id);
        Unite unite = uniteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(unite));
    }

    /**
     * DELETE  /unites/:id : delete the "id" unite.
     *
     * @param id the id of the unite to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unites/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnite(@PathVariable Long id) {
        log.debug("REST request to delete Unite : {}", id);
        uniteRepository.delete(id);
        uniteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/unites?query=:query : search for the unite corresponding
     * to the query.
     *
     * @param query the query of the unite search 
     * @return the result of the search
     */
    @GetMapping("/_search/unites")
    @Timed
    public List<Unite> searchUnites(@RequestParam String query) {
        log.debug("REST request to search Unites for query {}", query);
        return StreamSupport
            .stream(uniteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
