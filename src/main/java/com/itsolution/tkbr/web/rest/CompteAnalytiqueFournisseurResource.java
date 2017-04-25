package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.CompteAnalytiqueFournisseur;

import com.itsolution.tkbr.repository.CompteAnalytiqueFournisseurRepository;
import com.itsolution.tkbr.repository.search.CompteAnalytiqueFournisseurSearchRepository;
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
 * REST controller for managing CompteAnalytiqueFournisseur.
 */
@RestController
@RequestMapping("/api")
public class CompteAnalytiqueFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(CompteAnalytiqueFournisseurResource.class);

    private static final String ENTITY_NAME = "compteAnalytiqueFournisseur";
        
    private final CompteAnalytiqueFournisseurRepository compteAnalytiqueFournisseurRepository;

    private final CompteAnalytiqueFournisseurSearchRepository compteAnalytiqueFournisseurSearchRepository;

    public CompteAnalytiqueFournisseurResource(CompteAnalytiqueFournisseurRepository compteAnalytiqueFournisseurRepository, CompteAnalytiqueFournisseurSearchRepository compteAnalytiqueFournisseurSearchRepository) {
        this.compteAnalytiqueFournisseurRepository = compteAnalytiqueFournisseurRepository;
        this.compteAnalytiqueFournisseurSearchRepository = compteAnalytiqueFournisseurSearchRepository;
    }

    /**
     * POST  /compte-analytique-fournisseurs : Create a new compteAnalytiqueFournisseur.
     *
     * @param compteAnalytiqueFournisseur the compteAnalytiqueFournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteAnalytiqueFournisseur, or with status 400 (Bad Request) if the compteAnalytiqueFournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-analytique-fournisseurs")
    @Timed
    public ResponseEntity<CompteAnalytiqueFournisseur> createCompteAnalytiqueFournisseur(@Valid @RequestBody CompteAnalytiqueFournisseur compteAnalytiqueFournisseur) throws URISyntaxException {
        log.debug("REST request to save CompteAnalytiqueFournisseur : {}", compteAnalytiqueFournisseur);
        if (compteAnalytiqueFournisseur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compteAnalytiqueFournisseur cannot already have an ID")).body(null);
        }
        CompteAnalytiqueFournisseur result = compteAnalytiqueFournisseurRepository.save(compteAnalytiqueFournisseur);
        compteAnalytiqueFournisseurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/compte-analytique-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-analytique-fournisseurs : Updates an existing compteAnalytiqueFournisseur.
     *
     * @param compteAnalytiqueFournisseur the compteAnalytiqueFournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteAnalytiqueFournisseur,
     * or with status 400 (Bad Request) if the compteAnalytiqueFournisseur is not valid,
     * or with status 500 (Internal Server Error) if the compteAnalytiqueFournisseur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-analytique-fournisseurs")
    @Timed
    public ResponseEntity<CompteAnalytiqueFournisseur> updateCompteAnalytiqueFournisseur(@Valid @RequestBody CompteAnalytiqueFournisseur compteAnalytiqueFournisseur) throws URISyntaxException {
        log.debug("REST request to update CompteAnalytiqueFournisseur : {}", compteAnalytiqueFournisseur);
        if (compteAnalytiqueFournisseur.getId() == null) {
            return createCompteAnalytiqueFournisseur(compteAnalytiqueFournisseur);
        }
        CompteAnalytiqueFournisseur result = compteAnalytiqueFournisseurRepository.save(compteAnalytiqueFournisseur);
        compteAnalytiqueFournisseurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteAnalytiqueFournisseur.getId().toString()))
            .body(result);
    }

   /**
     * GET  /compte-analytique-fournisseurs : get all the compteAnalytiqueFournisseurs.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compteAnalytiqueFournisseurs in body
     */
    @GetMapping("/compte-analytique-fournisseurs")
    @Timed
    public ResponseEntity<List<CompteAnalytiqueFournisseur>> getAllCompteAnalytiqueFournisseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CompteAnalytiqueFournisseurs");
        Page<CompteAnalytiqueFournisseur> page = compteAnalytiqueFournisseurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytique-fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /compte-analytique-fournisseurs/:id : get the "id" compteAnalytiqueFournisseur.
     *
     * @param id the id of the compteAnalytiqueFournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteAnalytiqueFournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/compte-analytique-fournisseurs/{id}")
    @Timed
    public ResponseEntity<CompteAnalytiqueFournisseur> getCompteAnalytiqueFournisseur(@PathVariable Long id) {
        log.debug("REST request to get CompteAnalytiqueFournisseur : {}", id);
        CompteAnalytiqueFournisseur compteAnalytiqueFournisseur = compteAnalytiqueFournisseurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteAnalytiqueFournisseur));
    }

    /**
     * DELETE  /compte-analytique-fournisseurs/:id : delete the "id" compteAnalytiqueFournisseur.
     *
     * @param id the id of the compteAnalytiqueFournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-analytique-fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteAnalytiqueFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete CompteAnalytiqueFournisseur : {}", id);
        compteAnalytiqueFournisseurRepository.delete(id);
        compteAnalytiqueFournisseurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/compte-analytique-fournisseurs?query=:query : search for the compteAnalytiqueFournisseur corresponding
     * to the query.
     *
     * @param query the query of the compteAnalytiqueFournisseur search 
     * @return the result of the search
     */
    @GetMapping("/_search/compte-analytique-fournisseurs")
    @Timed
    public List<CompteAnalytiqueFournisseur> searchCompteAnalytiqueFournisseurs(@RequestParam String query) {
        log.debug("REST request to search CompteAnalytiqueFournisseurs for query {}", query);
        return StreamSupport
            .stream(compteAnalytiqueFournisseurSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}