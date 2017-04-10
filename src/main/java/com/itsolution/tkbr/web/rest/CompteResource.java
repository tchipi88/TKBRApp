package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Compte;

import com.itsolution.tkbr.repository.CompteRepository;
import com.itsolution.tkbr.repository.search.CompteSearchRepository;
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
 * REST controller for managing Compte.
 */
@RestController
@RequestMapping("/api")
public class CompteResource {

    private final Logger log = LoggerFactory.getLogger(CompteResource.class);

    private static final String ENTITY_NAME = "compte";
        
    private final CompteRepository compteRepository;

    private final CompteSearchRepository compteSearchRepository;

    public CompteResource(CompteRepository compteRepository, CompteSearchRepository compteSearchRepository) {
        this.compteRepository = compteRepository;
        this.compteSearchRepository = compteSearchRepository;
    }

    /**
     * POST  /comptes : Create a new compte.
     *
     * @param compte the compte to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compte, or with status 400 (Bad Request) if the compte has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comptes")
    @Timed
    public ResponseEntity<Compte> createCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to save Compte : {}", compte);
        if (compte.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compte cannot already have an ID")).body(null);
        }
        Compte result = compteRepository.save(compte);
        compteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/comptes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comptes : Updates an existing compte.
     *
     * @param compte the compte to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compte,
     * or with status 400 (Bad Request) if the compte is not valid,
     * or with status 500 (Internal Server Error) if the compte couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comptes")
    @Timed
    public ResponseEntity<Compte> updateCompte(@Valid @RequestBody Compte compte) throws URISyntaxException {
        log.debug("REST request to update Compte : {}", compte);
        if (compte.getId() == null) {
            return createCompte(compte);
        }
        Compte result = compteRepository.save(compte);
        compteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compte.getId().toString()))
            .body(result);
    }

   /**
     * GET  /comptes : get all the comptes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of comptes in body
     */
    @GetMapping("/comptes")
    @Timed
    public ResponseEntity<List<Compte>> getAllComptes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Comptes");
        Page<Compte> page = compteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comptes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comptes/:id : get the "id" compte.
     *
     * @param id the id of the compte to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compte, or with status 404 (Not Found)
     */
    @GetMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<Compte> getCompte(@PathVariable Long id) {
        log.debug("REST request to get Compte : {}", id);
        Compte compte = compteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compte));
    }

    /**
     * DELETE  /comptes/:id : delete the "id" compte.
     *
     * @param id the id of the compte to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comptes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompte(@PathVariable Long id) {
        log.debug("REST request to delete Compte : {}", id);
        compteRepository.delete(id);
        compteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/comptes?query=:query : search for the compte corresponding
     * to the query.
     *
     * @param query the query of the compte search 
     * @return the result of the search
     */
    @GetMapping("/_search/comptes")
    @Timed
    public List<Compte> searchComptes(@RequestParam String query) {
        log.debug("REST request to search Comptes for query {}", query);
        return StreamSupport
            .stream(compteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
