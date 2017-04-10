package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Access;

import com.itsolution.tkbr.repository.AccessRepository;
import com.itsolution.tkbr.repository.search.AccessSearchRepository;
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
 * REST controller for managing Access.
 */
@RestController
@RequestMapping("/api")
public class AccessResource {

    private final Logger log = LoggerFactory.getLogger(AccessResource.class);

    private static final String ENTITY_NAME = "access";
        
    private final AccessRepository accessRepository;

    private final AccessSearchRepository accessSearchRepository;

    public AccessResource(AccessRepository accessRepository, AccessSearchRepository accessSearchRepository) {
        this.accessRepository = accessRepository;
        this.accessSearchRepository = accessSearchRepository;
    }

    /**
     * POST  /accesss : Create a new access.
     *
     * @param access the access to create
     * @return the ResponseEntity with status 201 (Created) and with body the new access, or with status 400 (Bad Request) if the access has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/accesss")
    @Timed
    public ResponseEntity<Access> createAccess(@Valid @RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to save Access : {}", access);
        if (access.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new access cannot already have an ID")).body(null);
        }
        Access result = accessRepository.save(access);
        accessSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/accesss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /accesss : Updates an existing access.
     *
     * @param access the access to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated access,
     * or with status 400 (Bad Request) if the access is not valid,
     * or with status 500 (Internal Server Error) if the access couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/accesss")
    @Timed
    public ResponseEntity<Access> updateAccess(@Valid @RequestBody Access access) throws URISyntaxException {
        log.debug("REST request to update Access : {}", access);
        if (access.getId() == null) {
            return createAccess(access);
        }
        Access result = accessRepository.save(access);
        accessSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, access.getId().toString()))
            .body(result);
    }

   /**
     * GET  /accesss : get all the accesss.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accesss in body
     */
    @GetMapping("/accesss")
    @Timed
    public ResponseEntity<List<Access>> getAllAccesss(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Accesss");
        Page<Access> page = accessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/accesss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /accesss/:id : get the "id" access.
     *
     * @param id the id of the access to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the access, or with status 404 (Not Found)
     */
    @GetMapping("/accesss/{id}")
    @Timed
    public ResponseEntity<Access> getAccess(@PathVariable Long id) {
        log.debug("REST request to get Access : {}", id);
        Access access = accessRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(access));
    }

    /**
     * DELETE  /accesss/:id : delete the "id" access.
     *
     * @param id the id of the access to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/accesss/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccess(@PathVariable Long id) {
        log.debug("REST request to delete Access : {}", id);
        accessRepository.delete(id);
        accessSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/accesss?query=:query : search for the access corresponding
     * to the query.
     *
     * @param query the query of the access search 
     * @return the result of the search
     */
    @GetMapping("/_search/accesss")
    @Timed
    public List<Access> searchAccesss(@RequestParam String query) {
        log.debug("REST request to search Accesss for query {}", query);
        return StreamSupport
            .stream(accessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
