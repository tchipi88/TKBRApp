package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.EmployeFonction;

import com.itsolution.tkbr.repository.EmployeFonctionRepository;
import com.itsolution.tkbr.repository.search.EmployeFonctionSearchRepository;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
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

/**
 * REST controller for managing EmployeFonction.
 */
@RestController
@RequestMapping("/api")
public class EmployeFonctionResource {

    private final Logger log = LoggerFactory.getLogger(EmployeFonctionResource.class);

    private static final String ENTITY_NAME = "employeFonction";
        
    private final EmployeFonctionRepository employeFonctionRepository;

    private final EmployeFonctionSearchRepository employeFonctionSearchRepository;

    public EmployeFonctionResource(EmployeFonctionRepository employeFonctionRepository, EmployeFonctionSearchRepository employeFonctionSearchRepository) {
        this.employeFonctionRepository = employeFonctionRepository;
        this.employeFonctionSearchRepository = employeFonctionSearchRepository;
    }

    /**
     * POST  /employe-fonctions : Create a new employeFonction.
     *
     * @param employeFonction the employeFonction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeFonction, or with status 400 (Bad Request) if the employeFonction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employe-fonctions")
    @Timed
    public ResponseEntity<EmployeFonction> createEmployeFonction(@Valid @RequestBody EmployeFonction employeFonction) throws URISyntaxException {
        log.debug("REST request to save EmployeFonction : {}", employeFonction);
        if (employeFonction.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employeFonction cannot already have an ID")).body(null);
        }
        EmployeFonction result = employeFonctionRepository.save(employeFonction);
        employeFonctionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employe-fonctions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employe-fonctions : Updates an existing employeFonction.
     *
     * @param employeFonction the employeFonction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeFonction,
     * or with status 400 (Bad Request) if the employeFonction is not valid,
     * or with status 500 (Internal Server Error) if the employeFonction couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employe-fonctions")
    @Timed
    public ResponseEntity<EmployeFonction> updateEmployeFonction(@Valid @RequestBody EmployeFonction employeFonction) throws URISyntaxException {
        log.debug("REST request to update EmployeFonction : {}", employeFonction);
        if (employeFonction.getId() == null) {
            return createEmployeFonction(employeFonction);
        }
        EmployeFonction result = employeFonctionRepository.save(employeFonction);
        employeFonctionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeFonction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employe-fonctions : get all the employeFonctions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeFonctions in body
     */
    @GetMapping("/employe-fonctions")
    @Timed
    public List<EmployeFonction> getAllEmployeFonctions() {
        log.debug("REST request to get all EmployeFonctions");
        List<EmployeFonction> employeFonctions = employeFonctionRepository.findAll();
        return employeFonctions;
    }

    /**
     * GET  /employe-fonctions/:id : get the "id" employeFonction.
     *
     * @param id the id of the employeFonction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeFonction, or with status 404 (Not Found)
     */
    @GetMapping("/employe-fonctions/{id}")
    @Timed
    public ResponseEntity<EmployeFonction> getEmployeFonction(@PathVariable Long id) {
        log.debug("REST request to get EmployeFonction : {}", id);
        EmployeFonction employeFonction = employeFonctionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeFonction));
    }

    /**
     * DELETE  /employe-fonctions/:id : delete the "id" employeFonction.
     *
     * @param id the id of the employeFonction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employe-fonctions/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeFonction(@PathVariable Long id) {
        log.debug("REST request to delete EmployeFonction : {}", id);
        employeFonctionRepository.delete(id);
        employeFonctionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/employe-fonctions?query=:query : search for the employeFonction corresponding
     * to the query.
     *
     * @param query the query of the employeFonction search 
     * @return the result of the search
     */
    @GetMapping("/_search/employe-fonctions")
    @Timed
    public List<EmployeFonction> searchEmployeFonctions(@RequestParam String query) {
        log.debug("REST request to search EmployeFonctions for query {}", query);
        return StreamSupport
            .stream(employeFonctionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}