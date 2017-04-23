package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Local;

import com.itsolution.tkbr.repository.LocalRepository;
import com.itsolution.tkbr.repository.search.LocalSearchRepository;
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
 * REST controller for managing Local.
 */
@RestController
@RequestMapping("/api")
public class LocalResource {

    private final Logger log = LoggerFactory.getLogger(LocalResource.class);

    private static final String ENTITY_NAME = "local";
        
    private final LocalRepository localRepository;

    private final LocalSearchRepository localSearchRepository;

    public LocalResource(LocalRepository localRepository, LocalSearchRepository localSearchRepository) {
        this.localRepository = localRepository;
        this.localSearchRepository = localSearchRepository;
    }

    /**
     * POST  /locals : Create a new local.
     *
     * @param local the local to create
     * @return the ResponseEntity with status 201 (Created) and with body the new local, or with status 400 (Bad Request) if the local has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/locals")
    @Timed
    public ResponseEntity<Local> createLocal(@Valid @RequestBody Local local) throws URISyntaxException {
        log.debug("REST request to save Local : {}", local);
        if (local.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new local cannot already have an ID")).body(null);
        }
        Local result = localRepository.save(local);
        localSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/locals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /locals : Updates an existing local.
     *
     * @param local the local to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated local,
     * or with status 400 (Bad Request) if the local is not valid,
     * or with status 500 (Internal Server Error) if the local couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/locals")
    @Timed
    public ResponseEntity<Local> updateLocal(@Valid @RequestBody Local local) throws URISyntaxException {
        log.debug("REST request to update Local : {}", local);
        if (local.getId() == null) {
            return createLocal(local);
        }
        Local result = localRepository.save(local);
        localSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, local.getId().toString()))
            .body(result);
    }

   /**
     * GET  /locals : get all the locals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of locals in body
     */
    @GetMapping("/locals")
    @Timed
    public ResponseEntity<List<Local>> getAllLocals(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Locals");
        Page<Local> page = localRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/locals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /locals/:id : get the "id" local.
     *
     * @param id the id of the local to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the local, or with status 404 (Not Found)
     */
    @GetMapping("/locals/{id}")
    @Timed
    public ResponseEntity<Local> getLocal(@PathVariable Long id) {
        log.debug("REST request to get Local : {}", id);
        Local local = localRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(local));
    }

    /**
     * DELETE  /locals/:id : delete the "id" local.
     *
     * @param id the id of the local to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/locals/{id}")
    @Timed
    public ResponseEntity<Void> deleteLocal(@PathVariable Long id) {
        log.debug("REST request to delete Local : {}", id);
        localRepository.delete(id);
        localSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/locals?query=:query : search for the local corresponding
     * to the query.
     *
     * @param query the query of the local search 
     * @return the result of the search
     */
    @GetMapping("/_search/locals")
    @Timed
    public List<Local> searchLocals(@RequestParam String query) {
        log.debug("REST request to search Locals for query {}", query);
        return StreamSupport
            .stream(localSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
